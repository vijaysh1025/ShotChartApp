package com.vijay.nbashottracker.model.playbyplay


import com.google.gson.annotations.SerializedName


data class Home(

	@field:SerializedName("market")
	val market: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("points")
	val points: Int? = null
)