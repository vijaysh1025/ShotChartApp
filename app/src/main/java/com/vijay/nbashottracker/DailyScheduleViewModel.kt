package com.vijay.nbashottracker

import com.vijay.nbashottracker.datamodel.IDataModel
import com.vijay.nbashottracker.model.dailyschedule.Game
import com.vijay.nbashottracker.schedulers.ISchedulerProvider
import com.vijay.nbashottracker.state.IAppState
import io.reactivex.Observable
import io.reactivex.annotations.NonNull
import io.reactivex.subjects.BehaviorSubject
import java.time.LocalDate
import java.util.concurrent.TimeUnit

class DailyScheduleViewModel
constructor(@NonNull dataModel: IDataModel, @NonNull schedulerProvider:ISchedulerProvider, @NonNull appState:IAppState){

    @NonNull
    private val mDataModel:IDataModel = dataModel

    @NonNull
    private val mSchedulerProvider:ISchedulerProvider = schedulerProvider

    @NonNull
    private val mAppState:IAppState = appState;

    fun getGames():Observable<List<Game>> {
        return mAppState
            .mSelectedDate
            .observeOn(mSchedulerProvider.computation())
            .debounce(2000, TimeUnit.MILLISECONDS)
            .flatMap { date -> mDataModel.getGames(date)?.toObservable() }
    }

    fun getDateSubject():BehaviorSubject<LocalDate>{
        return  mAppState.mSelectedDate
    }

    fun dateSelected(@NonNull date:LocalDate) {
        mAppState.mSelectedDate.onNext(date)
    }

    fun getCurrentGameSubject():BehaviorSubject<Game>{
        return mAppState.mSelectedGame
    }

    fun gameSelected(@NonNull game:Game){
        mAppState.mSelectedGame.onNext(game)
    }


}