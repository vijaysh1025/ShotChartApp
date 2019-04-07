package com.vijay.nbashottracker.state.objects

import com.vijay.nbashottracker.model.dailyschedule.Game

class GameItem
constructor(game:Game?=null){
    val id:String = game?.id?:""
    val homeTeam:Team = Team(game?.home?.id?:"",game?.home?.name?:"", game?.home?.alias?:"")
    val awayTeam:Team = Team(game?.away?.id?:"",game?.away?.name?:"", game?.away?.alias?:"")

    data class Team(val id:String, val name:String, val alias:String)
}