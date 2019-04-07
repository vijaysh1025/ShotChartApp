package com.vijay.nbashottracker.feature.games.viewmodels

import com.vijay.nbashottracker.core.schedulers.ISchedulerProvider
import com.vijay.nbashottracker.feature.games.state.AppState
import com.vijay.nbashottracker.feature.games.state.IAppState
import com.vijay.nbashottracker.feature.games.state.TeamType
import com.vijay.nbashottracker.feature.games.state.objects.GameItem
import com.vijay.nbashottracker.feature.games.state.objects.PlayerItem
import com.vijay.nbashottracker.feature.games.state.objects.PlayerStats
import com.vijay.nbashottracker.feature.games.usecases.GetTeamPlayers
import io.reactivex.Observable
import io.reactivex.annotations.NonNull
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ShotChartViewModel
@Inject constructor(private val getTeamPlayers: GetTeamPlayers, private val schedulerProvider:ISchedulerProvider, private val appState: IAppState){
    
    fun getCurrentGameSubject():BehaviorSubject<GameItem>{
        return appState.mSelectedGame
    }

    fun getTeamPlayers():Observable<List<PlayerItem>>?{
        return appState.mSelectedTeam
            .observeOn(schedulerProvider.computation())
            .debounce(2000,TimeUnit.MILLISECONDS)
            .flatMap{ t: TeamType ->
               getTeamPlayers.For(GetTeamPlayers.Params(appState.mSelectedGame.value!!.id,t== TeamType.HOME))
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
        appState.mSelectedGame.onNext(com.vijay.nbashottracker.feature.games.state.AppState.EMPTY_GAME)
    }

    fun statsCleared(){
        appState.mSelectedGamePlayerStats.onNext(com.vijay.nbashottracker.feature.games.state.AppState.EMPTY_STATS)
    }

    fun playerSelected(@NonNull playerId:String){
        appState.mSelectedPlayer.onNext(playerId)
    }

}