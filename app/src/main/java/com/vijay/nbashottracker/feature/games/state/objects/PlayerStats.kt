package com.vijay.nbashottracker.feature.games.state.objects

import com.vijay.nbashottracker.feature.games.model.playbyplay.EventsItem
import com.vijay.nbashottracker.feature.games.model.playbyplay.Location
import com.vijay.nbashottracker.feature.games.model.playbyplay.Player

/**
 * Data model for PlayerStats used by GetPlayerStats Use Case
 */
class PlayerStats
constructor(_player: Player) {
    val playerNumber: String? = _player.jerseyNumber
    val playerName: String? = _player.fullName
    val fieldGoalEvents: MutableList<FieldGoalEvent> = mutableListOf()
}

/**
 * Field Goal Events that take shot location data from API and normalize it for easy consumption
 * for the UI Shot Chart Map
 */
class FieldGoalEvent
constructor(event: EventsItem) {

    companion object {
        const val CourtWidth = 1128
        const val CourtHeight = 600
    }

    val positionX: Float? = this.setPosY(event.location)
    val positionY: Float? = this.setPosX(event.location)
    val isMade: Boolean = event.eventType!!.contains("made")

    private fun setPosX(location: Location?): Float {
        var posX: Float
        if (location!!.coordX.toFloat() < 564) {
            posX = (CourtHeight - location.coordY.toFloat()) / 600
        } else {
            posX = location.coordY.toFloat() / 600
        }
        return posX
    }

    private fun setPosY(location: Location?): Float {
        var posY: Float
        if (location!!.coordX.toFloat() < 564) {
            posY = location.coordX.toFloat() / 600
        } else {
            posY = (CourtWidth - location.coordX.toFloat()) / 600
        }
        return posY
    }
}
