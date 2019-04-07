package com.vijay.nbashottracker.state.objects

import com.vijay.nbashottracker.model.summary.Player

class PlayerItem
constructor(player: Player){
    val id:String = player.id!!
    val name:String = player.fullName!!
    val number:String = player.jerseyNumber!!
}