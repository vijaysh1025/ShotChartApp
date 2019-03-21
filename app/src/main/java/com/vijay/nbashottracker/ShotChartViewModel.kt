package com.vijay.nbashottracker

import android.graphics.Point
import android.util.Log
import com.vijay.nbashottracker.datamodel.IDataModel
import com.vijay.nbashottracker.model.dailyschedule.Game
import com.vijay.nbashottracker.model.playbyplay.EventsItem
import com.vijay.nbashottracker.model.playbyplay.Location
import com.vijay.nbashottracker.model.playbyplay.PlayByPlay
import com.vijay.nbashottracker.model.summary.PlayersItem
import com.vijay.nbashottracker.schedulers.ISchedulerProvider
import com.vijay.nbashottracker.state.IAppState
import com.vijay.nbashottracker.state.ShotState
import com.vijay.nbashottracker.state.TeamType
import com.vijay.nbashottracker.state.objects.PlayerStats
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

    fun getTeamPlayers():Observable<List<PlayersItem?>>?{
        return mAppState.mSelectedTeam
            .observeOn(mSchedulerProvider.computation())
            .flatMap{ t:TeamType ->
                mDataModel.getTeamPlayers(mAppState.mSelectedGame.value!!.id, t==TeamType.HOME)?.toObservable() }
    }

    fun teamSelected(teamType: TeamType){
        mAppState.mSelectedTeam.onNext(teamType)
    }


    fun getPlayerStats():Observable<PlayerStats>?{
        return mAppState.mSelectedPlayer
            ?.observeOn(mSchedulerProvider.computation())
            ?.filter{it->mAppState.mSelectedGamePlayerStats.value?.containsKey(it)?:false}
            ?.flatMap { it->Observable.just(mAppState.mSelectedGamePlayerStats.value!![it]) }
    }


    fun gameSelected(@NonNull game:Game){
        mAppState.mSelectedGame.onNext(game)
    }

    fun playerSelected(@NonNull playerId:String){
        mAppState.mSelectedPlayer.onNext(playerId)
    }





}