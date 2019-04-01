package com.vijay.nba.domain.model

class Game(
    private val _id:String?,
    private val _venue: Venue?,
    private val _home: Team?,
    private val _away: Team?){
    val id get() = _id ?: ""
    val venue get() = _venue ?: ""
    val home get() = _home
    val away get() = _away

}

class Venue(
    private val _id:String?,
    private val _name:String?){
    val id get() = _id ?: ""
    val name get() = _name ?: ""
}

class Team(
    private val _id:String?,
    private val _name:String?,
    private val _alias:String?
){
    val id get() = _id ?: ""
    val name get() = _name ?: ""
    val alias get() = _alias ?: ""
}