package com.vijay.nbashottracker.model.summary


import com.google.gson.annotations.SerializedName


data class MostUnanswered(

	@field:SerializedName("opp_score")
	val oppScore: Int? = null,

	@field:SerializedName("own_score")
	val ownScore: Int? = null,

	@field:SerializedName("points")
	val points: Int? = null
)