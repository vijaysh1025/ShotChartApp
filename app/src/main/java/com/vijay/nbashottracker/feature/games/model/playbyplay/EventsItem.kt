package com.vijay.nbashottracker.feature.games.model.playbyplay


import com.google.gson.annotations.SerializedName


data class EventsItem(

    @field:SerializedName("event_type")
	val eventType: String? = null,

    @field:SerializedName("home_points")
	val homePoints: Int? = null,

    @field:SerializedName("away_points")
	val awayPoints: Int? = null,

    @field:SerializedName("possession")
	val possession: com.vijay.nbashottracker.feature.games.model.playbyplay.Possession? = null,

    @field:SerializedName("description")
	val description: String? = null,

    @field:SerializedName("id")
	val id: String? = null,

    @field:SerializedName("clock")
	val clock: String? = null,

    @field:SerializedName("updated")
	val updated: String? = null,

    @field:SerializedName("attribution")
	val attribution: com.vijay.nbashottracker.feature.games.model.playbyplay.Attribution? = null,

    @field:SerializedName("location")
	val location: com.vijay.nbashottracker.feature.games.model.playbyplay.Location? = null,

    @field:SerializedName("statistics")
	val statistics: List<com.vijay.nbashottracker.feature.games.model.playbyplay.StatisticsItem?>? = null,

    @field:SerializedName("turnover_type")
	val turnoverType: String? = null,

    @field:SerializedName("attempt")
	val attempt: String? = null,

    @field:SerializedName("duration")
	val duration: Int? = null
)