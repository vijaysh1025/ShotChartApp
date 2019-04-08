package com.vijay.nbashottracker.feature.games.state.objects

import com.vijay.nbashottracker.feature.games.model.dailyschedule.Game

/**
 * Data Model for Game used by GetGames Use Case.
 */
class GameItem
constructor(game: com.vijay.nbashottracker.feature.games.model.dailyschedule.Game?=null){
    val id:String = game?.id?:""
    val homeTeam: Team =
        Team(
            game?.home?.id ?: "",
            game?.home?.name ?: "",
            game?.home?.alias ?: ""
        )
    val awayTeam: Team =
        Team(
            game?.away?.id ?: "",
            game?.away?.name ?: "",
            game?.away?.alias ?: ""
        )

    data class Team(val id:String, val name:String, val alias:String)
}