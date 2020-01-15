package com.vijay.nbashottracker.feature.games.model.playbyplay

import com.google.gson.annotations.SerializedName

data class PlayByPlay(

    @field:SerializedName("coverage")
    val coverage: String? = null,

    @field:SerializedName("away")
    val away: Away? = null,

    @field:SerializedName("scheduled")
    val scheduled: String? = null,

    @field:SerializedName("entry_mode")
    val entryMode: String? = null,

    @field:SerializedName("clock")
    val clock: String? = null,

    @field:SerializedName("home")
    val home: Home? = null,

    @field:SerializedName("duration")
    val duration: String? = null,

    @field:SerializedName("times_tied")
    val timesTied: Int? = null,

    @field:SerializedName("sr_id")
    val srId: String? = null,

    @field:SerializedName("lead_changes")
    val leadChanges: Int? = null,

    @field:SerializedName("periods")
    val periods: List<PeriodsItem?>? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("track_on_court")
    val trackOnCourt: Boolean? = null,

    @field:SerializedName("attendance")
    val attendance: Int? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("neutral_site")
    val neutralSite: Boolean? = null,

    @field:SerializedName("quarter")
    val quarter: Int? = null
)