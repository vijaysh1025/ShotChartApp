package com.vijay.nbashottracker.feature.games.model.summary


import com.google.gson.annotations.SerializedName


data class Away(

    @field:SerializedName("market")
	val market: String? = null,

    @field:SerializedName("coaches")
	val coaches: List<com.vijay.nbashottracker.feature.games.model.summary.CoachesItem?>? = null,

    @field:SerializedName("sr_id")
	val srId: String? = null,

    @field:SerializedName("scoring")
	val scoring: List<com.vijay.nbashottracker.feature.games.model.summary.ScoringItem?>? = null,

    @field:SerializedName("players")
	val players: List<com.vijay.nbashottracker.feature.games.model.summary.Player>? = null,

    @field:SerializedName("name")
	val name: String? = null,

    @field:SerializedName("id")
	val id: String? = null,

    @field:SerializedName("points")
	val points: Int? = null,

    @field:SerializedName("statistics")
	val statistics: com.vijay.nbashottracker.feature.games.model.summary.Statistics? = null
)