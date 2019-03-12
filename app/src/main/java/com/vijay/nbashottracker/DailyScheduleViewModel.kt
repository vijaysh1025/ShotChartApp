package com.vijay.nbashottracker

import com.vijay.nbashottracker.datamodel.DataModel
import com.vijay.nbashottracker.datamodel.IDataModel
import com.vijay.nbashottracker.model.APIClient
import com.vijay.nbashottracker.model.DataStore
import com.vijay.nbashottracker.model.Game
import com.vijay.nbashottracker.schedulers.ISchedulerProvider
import io.reactivex.Observable
import io.reactivex.annotations.NonNull
import io.reactivex.subjects.BehaviorSubject
import retrofit2.create
import java.time.LocalDate
import java.util.*
import java.util.concurrent.TimeUnit

class DailyScheduleViewModel
constructor(@NonNull dataModel: IDataModel?, @NonNull schedulerProvider:ISchedulerProvider?){

    @NonNull
    private val mDataModel:IDataModel? = dataModel

    @NonNull
    private val mSchedulerProvider:ISchedulerProvider? = schedulerProvider

    @NonNull
    private val mSelectedDate: BehaviorSubject<LocalDate> = BehaviorSubject.create()

    fun getGames():Observable<List<Game>> {
        return mSelectedDate
            .observeOn(mSchedulerProvider?.computation())
            .debounce(2000, TimeUnit.MILLISECONDS)
            .flatMap { date -> mDataModel?.getGames(date)?.toObservable() }
    }

    fun getDate():BehaviorSubject<LocalDate>{
        return mSelectedDate
    }

    fun dateSelected(@NonNull date:LocalDate) {
        mSelectedDate.onNext(date)
    }
}