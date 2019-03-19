package com.vijay.nbashottracker

import android.util.Log
import com.vijay.nbashottracker.datamodel.IDataModel
import com.vijay.nbashottracker.model.dailyschedule.Game
import com.vijay.nbashottracker.model.playbyplay.EventsItem
import com.vijay.nbashottracker.model.playbyplay.Location
import com.vijay.nbashottracker.model.playbyplay.PlayByPlay
import com.vijay.nbashottracker.model.summary.PlayersItem
import com.vijay.nbashottracker.schedulers.ISchedulerProvider
import com.vijay.nbashottracker.state.IAppState
import com.vijay.nbashottracker.state.TeamType
import io.reactivex.Observable
import io.reactivex.annotations.NonNull
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import java.time.LocalDate
import java.util.concurrent.TimeUnit

class ShotChartViewModel
constructor(@NonNull dataModel: IDataModel, @NonNull schedulerProvider:ISchedulerProvider, @NonNull appState:IAppState){

    @NonNull
    private val mDataModel:IDataModel = dataModel

    @NonNull
    private val mSchedulerProvider:ISchedulerProvider = schedulerProvider

    @NonNull
    private val mAppState:IAppState = appState;

    init {
        teamSelected(TeamType.HOME)
    }

    fun getCurrentGameSubject():BehaviorSubject<Game>{
        return mAppState.mSelectedGame
    }

    fun getSelectedTeamId():Observable<Boolean>{
        return mAppState
            .mSelectedTeam
            .observeOn(mSchedulerProvider.computation())
            .map { t:TeamType-> t==TeamType.HOME}
        }

    fun getTeamPlayers():Observable<List<PlayersItem?>>?{
        return mAppState.mSelectedTeam
            .observeOn(mSchedulerProvider.computation())
            .flatMap{ t:TeamType ->
                mDataModel.getTeamPlayers(mAppState.mSelectedGame.value!!.id, t==TeamType.HOME)?.toObservable() }
    }

    fun teamSelected(teamType: TeamType){
        mAppState.mSelectedTeam.onNext(teamType)
    }

    fun gameSelected(@NonNull game:Game){
        mAppState.mSelectedGame.onNext(game)
    }





}