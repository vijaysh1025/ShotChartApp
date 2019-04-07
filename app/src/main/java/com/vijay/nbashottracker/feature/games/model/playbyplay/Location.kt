package com.vijay.nbashottracker.feature.games.model.playbyplay


import com.google.gson.annotations.SerializedName


data class Location(

	@field:SerializedName("coord_x")
	val coordX: Int,

	@field:SerializedName("coord_y")
	val coordY: Int
)