package com.vijay.nba.data.entity.playbyplay


import com.google.gson.annotations.SerializedName


data class Away(

	@field:SerializedName("market")
	val market: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("points")
	val points: Int? = null
)