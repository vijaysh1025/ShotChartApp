package com.vijay.nbashottracker.model

import com.google.gson.annotations.SerializedName

data class DailySchedule(@SerializedName("games") private val _games:List<Game>){
    val games get() = _games?:""
}

data class Game(
    @SerializedName("id") private val _id:String?,
    @SerializedName("venue") private val _venue:Venue?,
    @SerializedName("home") private val _home:Team?,
    @SerializedName("away") private val _away:Team?){
    val id get() = _id ?: ""
    val venue get() = _venue ?: ""
    val home get() = _home ?: ""
    val away get() = _away ?: ""

}

data class Venue(
    @SerializedName("id") private val _id:String?,
    @SerializedName("name") private val _name:String?){
    val id get() = _id ?: ""
    val name get() = _name ?: ""
}

data class Team(
    @SerializedName("id") private val _id:String?,
    @SerializedName("name") private val _name:String?,
    @SerializedName("alias") private val _alias:String?
){
    val id get() = _id ?: ""
    val name get() = _name ?: ""
    val alias get() = _alias ?: ""
}
