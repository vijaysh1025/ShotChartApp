package com.vijay.nbashottracker.feature.games.model.playbyplay


import com.google.gson.annotations.SerializedName


data class Possession(

	@field:SerializedName("market")
	val market: String? = null,

	@field:SerializedName("sr_id")
	val srId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)