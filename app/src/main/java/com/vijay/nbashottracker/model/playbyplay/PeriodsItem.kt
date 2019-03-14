package com.vijay.nbashottracker.model.playbyplay


import com.google.gson.annotations.SerializedName


data class PeriodsItem(

	@field:SerializedName("number")
	val number: Int? = null,

	@field:SerializedName("sequence")
	val sequence: Int? = null,

	@field:SerializedName("scoring")
	val scoring: Scoring? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("events")
	val events: List<EventsItem?>? = null
)