package com.vijay.nbashottracker.model.summary


import com.google.gson.annotations.SerializedName


data class Home(

	@field:SerializedName("market")
	val market: String? = null,

	@field:SerializedName("coaches")
	val coaches: List<Any?>? = null,

	@field:SerializedName("sr_id")
	val srId: String? = null,

	@field:SerializedName("scoring")
	val scoring: List<Any?>? = null,

	@field:SerializedName("players")
	val players: List<PlayersItem>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("points")
	val points: Int? = null,

	@field:SerializedName("statistics")
	val statistics: Statistics? = null
)