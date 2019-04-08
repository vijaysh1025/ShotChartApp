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

/**
 * View Model to bind state data from AppState to View(ShotChartViewModel)
 */
class ShotChartViewModel
@Inject constructor(private val getTeamPlayers: GetTeamPlayers, private val schedulerProvider:ISchedulerProvider, private val appState: IAppState){

    // Get current game subject
    fun getCurrentGameSubject():BehaviorSubject<GameItem>{
        return appState.mSelectedGame
    }

    // Get Team Players base on selected team
    // Debounce quick changes to Team toggles
    fun getTeamPlayers():Observable<List<PlayerItem>>?{
        return appState.mSelectedTeam
            .observeOn(schedulerProvider.computation())
            .debounce(2000,TimeUnit.MILLISECONDS)
            .flatMap{ t: TeamType ->
               getTeamPlayers.For(GetTeamPlayers.Params(appState.mSelectedGame.value!!.id,t== TeamType.HOME))
                   .toObservable() }
    }

    // Update selected team
    fun teamSelected(teamType: TeamType){
        appState.mSelectedTeam.onNext(teamType)
    }

    // Get Team Selected Subject
    fun getTeamSelected():BehaviorSubject<TeamType>{
        return appState.mSelectedTeam
    }

    // Get Player stats and filter stats for player that was inactive
    fun getPlayerStats():Observable<PlayerStats>?{
        return appState.mSelectedPlayer
            .observeOn(schedulerProvider.computation())
            ?.filter{appState.mSelectedGamePlayerStats.value?.containsKey(it)?:false}
            ?.flatMap {Observable.just(appState.mSelectedGamePlayerStats.value!![it]) }
    }

    // Clear Selected Game when going back to DailyScheduleActivity
    fun gameCleared(){
        appState.mSelectedGame.onNext(AppState.EMPTY_GAME)
    }

    // Clear Stats when going back to DailyScheduleActivity
    fun statsCleared(){
        appState.mSelectedGamePlayerStats.onNext(AppState.EMPTY_STATS)
    }

    // Update Player selected
    fun playerSelected(@NonNull playerId:String){
        appState.mSelectedPlayer.onNext(playerId)
    }

}