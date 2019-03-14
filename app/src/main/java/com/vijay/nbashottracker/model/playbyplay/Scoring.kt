package com.vijay.nbashottracker.model.playbyplay


import com.google.gson.annotations.SerializedName


data class Scoring(

	@field:SerializedName("away")
	val away: Away? = null,

	@field:SerializedName("home")
	val home: Home? = null
)