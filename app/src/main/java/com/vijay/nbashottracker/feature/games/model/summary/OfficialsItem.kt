package com.vijay.nbashottracker.feature.games.model.summary


import com.google.gson.annotations.SerializedName


data class OfficialsItem(

	@field:SerializedName("number")
	val number: String? = null,

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("assignment")
	val assignment: String? = null,

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("experience")
	val experience: String? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null
)