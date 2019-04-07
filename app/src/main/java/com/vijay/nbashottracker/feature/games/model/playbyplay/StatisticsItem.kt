package com.vijay.nbashottracker.feature.games.model.playbyplay


import com.google.gson.annotations.SerializedName


data class StatisticsItem(

    @field:SerializedName("rebound_type")
	val reboundType: String? = null,

    @field:SerializedName("team")
	val team: com.vijay.nbashottracker.feature.games.model.playbyplay.Team? = null,

    @field:SerializedName("type")
	val type: String? = null,

    @field:SerializedName("player")
	val player: com.vijay.nbashottracker.feature.games.model.playbyplay.Player? = null
)