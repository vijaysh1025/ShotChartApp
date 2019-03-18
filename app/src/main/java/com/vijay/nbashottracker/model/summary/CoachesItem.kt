package com.vijay.nbashottracker.model.summary


import com.google.gson.annotations.SerializedName


data class CoachesItem(

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("position")
	val position: String? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null
)