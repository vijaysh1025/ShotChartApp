package com.vijay.nbashottracker.datamodel

import com.vijay.nbashottracker.model.DataStore
import com.vijay.nbashottracker.model.Game
import com.vijay.nbashottracker.model.APIClient
import com.vijay.nbashottracker.model.DailySchedule
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.annotations.NonNull
import io.reactivex.schedulers.Schedulers
import retrofit2.create
import java.time.LocalDate

class DataModel : IDataModel{
    @NonNull
    override fun getGames(localDate:LocalDate): Single<List<Game>>? {
        return APIClient.instance
            ?.create<DataStore>()
            ?.getScheduleOfDay(localDate.year.toString(), localDate.monthValue.toString(), localDate.dayOfMonth.toString())
            ?.subscribeOn(Schedulers.io())
            ?.map{it: DailySchedule -> it.games as List<Game>}
    }
}