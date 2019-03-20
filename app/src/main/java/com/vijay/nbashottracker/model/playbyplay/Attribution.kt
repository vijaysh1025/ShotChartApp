package com.vijay.nbashottracker.model.playbyplay


import com.google.gson.annotations.SerializedName


data class Attribution(

	@field:SerializedName("market")
	val market: String? = null,

	@field:SerializedName("sr_id")
	val srId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("team_basket")
	val teamBasket: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)