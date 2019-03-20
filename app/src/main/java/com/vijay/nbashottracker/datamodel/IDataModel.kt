package com.vijay.nbashottracker.datamodel

import com.vijay.nbashottracker.model.dailyschedule.Game
import com.vijay.nbashottracker.model.playbyplay.PlayByPlay
import com.vijay.nbashottracker.model.summary.PlayersItem
import io.reactivex.Single
import io.reactivex.annotations.NonNull
import java.time.LocalDate

interface IDataModel {

    @NonNull
    fun getGames(localDate: LocalDate): Single<List<Game>>?

    @NonNull
    fun getPlayByPlay(gameId: String): Single<PlayByPlay>?

    @NonNull
    fun getTeamPlayers(gameId: String, isHomeTeam:Boolean = true): Single<List<PlayersItem?>>?

}