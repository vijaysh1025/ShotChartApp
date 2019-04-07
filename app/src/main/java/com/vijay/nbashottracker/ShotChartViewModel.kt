package com.vijay.nbashottracker

import com.vijay.nbashottracker.core.schedulers.ISchedulerProvider
import com.vijay.nbashottracker.state.AppState
import com.vijay.nbashottracker.state.IAppState
import com.vijay.nbashottracker.state.TeamType
import com.vijay.nbashottracker.state.objects.GameItem
import com.vijay.nbashottracker.state.objects.PlayerItem
import com.vijay.nbashottracker.state.objects.PlayerStats
import com.vijay.nbashottracker.usecases.GetTeamPlayers
import io.reactivex.Observable
import io.reactivex.annotations.NonNull
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class ShotChartViewModel
@Inject constructor(private val getTeamPlayers: GetTeamPlayers, private val schedulerProvider:ISchedulerProvider, private val appState:IAppState){
    
    fun getCurrentGameSubject():BehaviorSubject<GameItem>{
        return appState.mSelectedGame
    }

    fun getTeamPlayers():Observable<List<PlayerItem>>?{
        return appState.mSelectedTeam
            .observeOn(schedulerProvider.computation())
            .flatMap{ t:TeamType ->
               getTeamPlayers.For(GetTeamPlayers.Params(appState.mSelectedGame.value!!.id,t==TeamType.HOME))
                   .toObservable() }
    }

    fun teamSelected(teamType: TeamType){
        appState.mSelectedTeam.onNext(teamType)
    }

    fun getTeamSelected():BehaviorSubject<TeamType>{
        return appState.mSelectedTeam
    }

    fun getPlayerStats():Observable<PlayerStats>?{
        return appState.mSelectedPlayer
            .observeOn(schedulerProvider.computation())
            ?.filter{appState.mSelectedGamePlayerStats.value?.containsKey(it)?:false}
            ?.flatMap {Observable.just(appState.mSelectedGamePlayerStats.value!![it]) }
    }

    fun gameCleared(){
        appState.mSelectedGame.onNext(AppState.EMPTY_GAME)
    }

    fun statsCleared(){
        appState.mSelectedGamePlayerStats.onNext(AppState.EMPTY_STATS)
    }

    fun playerSelected(@NonNull playerId:String){
        appState.mSelectedPlayer.onNext(playerId)
    }

}