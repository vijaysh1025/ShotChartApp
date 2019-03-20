package com.vijay.nbashottracker.model.summary

import com.google.gson.annotations.SerializedName

data class PlayersItem(

	@field:SerializedName("sr_id")
	val srId: String? = null,

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("starter")
	val starter: Boolean? = null,

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("active")
	val active: Boolean? = null,

	@field:SerializedName("jersey_number")
	val jerseyNumber: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("position")
	val position: String? = null,

	@field:SerializedName("primary_position")
	val primaryPosition: String? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null,

	@field:SerializedName("played")
	val played: Boolean? = null,

	@field:SerializedName("statistics")
	val statistics: Statistics? = null,

	@field:SerializedName("injuries")
	val injuries: List<InjuriesItem?>? = null
)