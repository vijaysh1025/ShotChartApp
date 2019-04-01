package com.vijay.nba.data.entity.summary


import com.google.gson.annotations.SerializedName


data class ScoringItem(

	@field:SerializedName("number")
	val number: Int? = null,

	@field:SerializedName("sequence")
	val sequence: Int? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("points")
	val points: Int? = null
)