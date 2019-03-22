package com.vijay.nbashottracker.state.objects

import com.vijay.nbashottracker.model.playbyplay.EventsItem
import com.vijay.nbashottracker.model.playbyplay.Location
import com.vijay.nbashottracker.model.playbyplay.Player

class PlayerStats
constructor(_player:Player){
    val playerNumber:String?=_player.jerseyNumber
    val playerName:String?=_player.fullName
    val fieldGoalEvents:MutableList<FieldGoalEvent> = mutableListOf()
}

class FieldGoalEvent
constructor(_event:EventsItem) {

    companion object {
        val CourtWidth = 1128
        val CourtHeight = 600
    }

    private val event: EventsItem = _event

    val positionX: Float? = this.setPosY(_event.location)
    val positionY: Float? = this.setPosX(_event.location)
    val isMade:Boolean = _event.eventType!!.contains("made")

    private fun setPosX(location: Location?):Float{
        var posX = 0f
        if(location!!.coordX.toFloat()<564){
            posX = (600-location!!.coordY.toFloat())/600
        }else{
            posX = location!!.coordY.toFloat()/600
        }
        return posX
    }

    private fun setPosY(location: Location?):Float{
        var posY = 0f
        if(location!!.coordX.toFloat()<564){
            posY = location!!.coordX.toFloat()/600
        }else{
            posY = (1128-location!!.coordX.toFloat())/600
        }
        return posY
    }


}
