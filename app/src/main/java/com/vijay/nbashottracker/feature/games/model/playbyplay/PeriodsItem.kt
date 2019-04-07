package com.vijay.nbashottracker.feature.games.model.playbyplay


import com.google.gson.annotations.SerializedName


data class PeriodsItem(

    @field:SerializedName("number")
	val number: Int? = null,

    @field:SerializedName("sequence")
	val sequence: Int? = null,

    @field:SerializedName("scoring")
	val scoring: com.vijay.nbashottracker.feature.games.model.playbyplay.Scoring? = null,

    @field:SerializedName("id")
	val id: String? = null,

    @field:SerializedName("type")
	val type: String? = null,

    @field:SerializedName("events")
	val events: List<com.vijay.nbashottracker.feature.games.model.playbyplay.EventsItem?>? = null
)