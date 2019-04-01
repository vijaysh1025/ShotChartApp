package com.vijay.nba.data.entity.playbyplay


import com.google.gson.annotations.SerializedName


data class EventsItem(

	@field:SerializedName("event_type")
	val eventType: String? = null,

	@field:SerializedName("home_points")
	val homePoints: Int? = null,

	@field:SerializedName("away_points")
	val awayPoints: Int? = null,

	@field:SerializedName("possession")
	val possession: Possession? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("clock")
	val clock: String? = null,

	@field:SerializedName("updated")
	val updated: String? = null,

	@field:SerializedName("attribution")
	val attribution: Attribution? = null,

	@field:SerializedName("location")
	val location: Location? = null,

	@field:SerializedName("statistics")
	val statistics: List<StatisticsItem?>? = null,

	@field:SerializedName("turnover_type")
	val turnoverType: String? = null,

	@field:SerializedName("attempt")
	val attempt: String? = null,

	@field:SerializedName("duration")
	val duration: Int? = null
)