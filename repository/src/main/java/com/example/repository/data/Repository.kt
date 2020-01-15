package com.vvechirko.repositoryapp.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.vvechirko.repositoryapp.data.api.ApiData
import com.vvechirko.repositoryapp.data.db.DbData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

object Repository {

    const val PAGE = "page"
    const val ID = "id"
    const val USER_ID = "userId"
    const val POST_ID = "postId"

    lateinit var appContext: Context

    fun isNetworkAvailable(): Boolean {
        val cm = appContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo?.isConnected ?: false
    }

    inline fun <reified Entity : Any> of(): Repo<Entity> {
        return Repo<Entity>(ApiData.of(Entity::class), DbData.of(Entity::class))
    }

    fun clearDatabase(): Completable {
        return Completable.fromCallable { DbData.clearDb() }
                .subscribeOn(Schedulers.io())
    }
}

class Repo<Entity : Any>(val api: DataSource<Entity>, val db: DataSource<Entity>) : DataSource<Entity> {

    override fun getAll(): Observable<List<Entity>> {
        return Observable.concatArrayEager(
            // get items from db first
            db.getAll().subscribeOn(Schedulers.io()),
            // get items from api if Network is Available
            Observable.defer {
                if (Repository.isNetworkAvailable())
                // get new items from api
                    api.getAll().subscribeOn(Schedulers.io())
                        .flatMap { l ->
                            // remove old items from db
                            db.removeAll()
                                // save new items from api to db
                                .andThen(db.saveAll(l))
                        }
                else
                // or return empty
                    Observable.empty()
            }.subscribeOn(Schedulers.io())
        )
    }

    override fun saveAll(list: List<Entity>): Observable<List<Entity>> {
        return Observable.defer {
            if (Repository.isNetworkAvailable())
            // save items to api first
                api.saveAll(list).subscribeOn(Schedulers.io())
                    // save items to db
                    .flatMap { db.saveAll(list) }
            else
                Observable.error(IllegalAccessError("You are offline"))
        }
    }

    override fun removeAll(list: List<Entity>): Completable {
        return Completable.defer {
            if (Repository.isNetworkAvailable())
            // remove items from api first
                api.removeAll(list).subscribeOn(Schedulers.io())
                    // remove items from db
                    .andThen(db.removeAll(list))
            else
                Completable.error(IllegalAccessError("You are offline"))
        }
    }

    override fun removeAll(): Completable {
        return Completable.defer {
            if (Repository.isNetworkAvailable())
            // remove items from api first
                api.removeAll().subscribeOn(Schedulers.io())
                    // remove items from db
                    .andThen(db.removeAll())
            else
                Completable.error(IllegalAccessError("You are offline"))
        }
    }

    override fun getAll(query: DataSource.Query<Entity>): Observable<List<Entity>> {
        return Observable.concatArrayEager(
            // get items from db first
            db.getAll(query).subscribeOn(Schedulers.io()),
            // get items from api if Network is Available
            Observable.defer {
                if (Repository.isNetworkAvailable())
                // get new items from api
                    api.getAll(query).subscribeOn(Schedulers.io())
//                                .flatMap { applyPaging(it, query) }
                        .flatMap { l ->
                            // get old items from db
                            db.getAll(query)
                                // remove old items from db
                                .flatMapCompletable { old -> db.removeAll(old) }
                                // save new items from api to db
                                .andThen(db.saveAll(l))
                        }
                else
                // or return empty
                    Observable.empty()
            }.subscribeOn(Schedulers.io())
        )
    }
}