package com.vijay.nbashottracker.datamodel

import android.util.EventLog
import com.vijay.nbashottracker.model.dailyschedule.Game
import com.vijay.nbashottracker.model.playbyplay.EventsItem
import com.vijay.nbashottracker.model.playbyplay.PlayByPlay
import com.vijay.nbashottracker.model.summary.PlayersItem
import com.vijay.nbashottracker.state.objects.PlayerStats
import io.reactivex.Single
import io.reactivex.annotations.NonNull
import java.time.LocalDate

interface IDataModel {

    @NonNull
    fun getGames(localDate: LocalDate): Single<List<Game>>?

    @NonNull
    fun getGameEvents(gameId: String): Single<List<EventsItem?>?>

    @NonNull
    fun getTeamPlayers(gameId: String, isHomeTeam:Boolean = true): Single<List<PlayersItem?>>?


    @NonNull
    fun getFieldFoalEvents(gameId: String):Single<List<EventsItem?>?>

    @NonNull
    fun getPlayerStats(gameId: String):Single<Map<String,PlayerStats>>
}