package com.vijay.nbashottracker.feature.games.state.objects

import com.vijay.nbashottracker.feature.games.model.playbyplay.EventsItem
import com.vijay.nbashottracker.feature.games.model.playbyplay.Location
import com.vijay.nbashottracker.feature.games.model.playbyplay.Player

/**
 * Data model for PlayerStats used by GetPlayerStats Use Case
 */
class PlayerStats
constructor(_player: com.vijay.nbashottracker.feature.games.model.playbyplay.Player){
    val playerNumber:String?=_player.jerseyNumber
    val playerName:String?=_player.fullName
    val fieldGoalEvents:MutableList<FieldGoalEvent> = mutableListOf()
}

/**
 * Field Goal Events that take shot location data from API and normalize it for easy consumption
 * for the UI Shot Chart Map
 */
class FieldGoalEvent
constructor(_event: com.vijay.nbashottracker.feature.games.model.playbyplay.EventsItem) {

    companion object {
        val CourtWidth = 1128
        val CourtHeight = 600
    }

    private val event: com.vijay.nbashottracker.feature.games.model.playbyplay.EventsItem = _event

    val positionX: Float? = this.setPosY(_event.location)
    val positionY: Float? = this.setPosX(_event.location)
    val isMade:Boolean = _event.eventType!!.contains("made")

    private fun setPosX(location: com.vijay.nbashottracker.feature.games.model.playbyplay.Location?):Float{
        var posX:Float
        if(location!!.coordX.toFloat()<564){
            posX = (600-location.coordY.toFloat())/600
        }else{
            posX = location.coordY.toFloat()/600
        }
        return posX
    }

    private fun setPosY(location: com.vijay.nbashottracker.feature.games.model.playbyplay.Location?):Float{
        var posY:Float
        if(location!!.coordX.toFloat()<564){
            posY = location.coordX.toFloat()/600
        }else{
            posY = (1128-location.coordX.toFloat())/600
        }
        return posY
    }
}
