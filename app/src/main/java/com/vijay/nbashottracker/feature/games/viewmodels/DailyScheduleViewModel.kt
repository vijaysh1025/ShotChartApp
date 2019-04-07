package com.vijay.nbashottracker.feature.games.viewmodels

import android.text.format.DateUtils
import com.vijay.nbashottracker.core.schedulers.ISchedulerProvider
import com.vijay.nbashottracker.feature.games.state.AppState
import com.vijay.nbashottracker.feature.games.state.IAppState
import com.vijay.nbashottracker.feature.games.state.objects.GameItem
import com.vijay.nbashottracker.feature.games.state.objects.PlayerStats
import com.vijay.nbashottracker.feature.games.usecases.GetGames
import com.vijay.nbashottracker.feature.games.usecases.GetPlayerStats
import io.reactivex.Observable
import io.reactivex.annotations.NonNull
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import java.time.LocalDate
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DailyScheduleViewModel
@Inject constructor(private val getGames: GetGames, private val getPlayerStats: GetPlayerStats, private val schedulerProvider:ISchedulerProvider, private val appState: IAppState){


    val maxDate:Long
    get() {
        return Date().time- DateUtils.DAY_IN_MILLIS
    }

    fun getGames():Observable<List<GameItem>> {
        return appState
            .mSelectedDate
            .observeOn(schedulerProvider.computation())
            .debounce(2000, TimeUnit.MILLISECONDS)
            .flatMap { date -> getGames.For(GetGames.Params(date)).toObservable() }
    }

    fun getPlayerStats():Observable<Map<String, PlayerStats>>{
        return appState
            .mSelectedGame
            .observeOn(schedulerProvider.computation())
            .filter { it != com.vijay.nbashottracker.feature.games.state.AppState.EMPTY_GAME }
            .flatMap{game->getPlayerStats.For(GetPlayerStats.Params(game.id)).toObservable()}
    }

    fun getDateSubject():BehaviorSubject<LocalDate>{
        return  appState.mSelectedDate
    }

    fun dateSelected(@NonNull date:LocalDate) {
        appState.mSelectedDate.onNext(date)
    }

    fun getCurrentGameSubject():BehaviorSubject<GameItem>{
        return appState.mSelectedGame
    }

    fun getPlayerStatsSubject():Subject<Map<String, PlayerStats>>{
        return appState.mSelectedGamePlayerStats
    }

    fun gameSelected(@NonNull game: GameItem){
        appState.mSelectedGame.onNext(game)
    }

    fun setPlayerStats(@NonNull playerStats:Map<String, PlayerStats>){
        appState.mSelectedGamePlayerStats.onNext(playerStats)
    }
}