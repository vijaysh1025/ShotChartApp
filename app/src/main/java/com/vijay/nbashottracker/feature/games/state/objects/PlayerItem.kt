package com.vijay.nbashottracker.feature.games.state.objects

import com.vijay.nbashottracker.feature.games.model.summary.Player

/**
 * Data model for Players used by GetTeamPlayers Use Case
 */
class PlayerItem
constructor(player: com.vijay.nbashottracker.feature.games.model.summary.Player){
    val id:String = player.id!!
    val name:String = player.fullName!!
    val number:String = player.jerseyNumber!!
}