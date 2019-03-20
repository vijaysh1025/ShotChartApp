package com.vijay.nbashottracker.model.playbyplay


import com.google.gson.annotations.SerializedName


data class StatisticsItem(

	@field:SerializedName("rebound_type")
	val reboundType: String? = null,

	@field:SerializedName("team")
	val team: Team? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("player")
	val player: Player? = null
)