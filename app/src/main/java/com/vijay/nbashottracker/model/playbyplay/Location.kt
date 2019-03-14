package com.vijay.nbashottracker.model.playbyplay


import com.google.gson.annotations.SerializedName


data class Location(

	@field:SerializedName("coord_x")
	val coordX: Int? = null,

	@field:SerializedName("coord_y")
	val coordY: Int? = null
)