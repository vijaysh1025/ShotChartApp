package com.vijay.nbashottracker.feature.games.model.playbyplay


import com.google.gson.annotations.SerializedName


data class Scoring(

    @field:SerializedName("away")
	val away: com.vijay.nbashottracker.feature.games.model.playbyplay.Away? = null,

    @field:SerializedName("home")
	val home: com.vijay.nbashottracker.feature.games.model.playbyplay.Home? = null
)