package com.vijay.nbashottracker.configuration

// Base url for sportradar api
const val BASE_URL ="https://api.sportradar.us"

// Sportradar trial api key
// Note: This key will not work once it reaches a rate limit of 1000
const val API_KEY = "fkgfzb9terxk7289s6t3u5bf"


object GlobalState {
    var BaseURL:String= BASE_URL
    get() {
        return BASE_URL
    }
}