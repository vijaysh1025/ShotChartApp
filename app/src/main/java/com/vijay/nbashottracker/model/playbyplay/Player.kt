package com.vijay.nbashottracker.model.playbyplay


import com.google.gson.annotations.SerializedName


data class Player(

	@field:SerializedName("sr_id")
	val srId: String? = null,

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("jersey_number")
	val jerseyNumber: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)