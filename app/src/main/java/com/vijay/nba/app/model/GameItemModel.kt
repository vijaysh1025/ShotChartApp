package com.vijay.nba.app.model

import com.vijay.nba.domain.model.Game
import java.lang.IllegalArgumentException

class GameItemModel(id:String){
    val id = id

    var homeTeam:String = ""
    var awayTeam:String = ""

    companion object {
        fun transform(gameItem: Game):GameItemModel{
            if(gameItem==null) throw IllegalArgumentException("Cannot transform null value")

            var gameItemModel=GameItemModel(gameItem.id)
            gameItemModel.homeTeam = gameItem.home.alias
            gameItemModel.awayTeam = gameItem.away.alias

            return  gameItemModel
        }
    }
}