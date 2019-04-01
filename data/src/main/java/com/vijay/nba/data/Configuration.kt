package com.vijay.nba.data

const val DEV = false

const val URL ="https://api.sportradar.us"

const val API_KEY = "fkgfzb9terxk7289s6t3u5bf"

object Configuration {
    var BaseURL:String?=null
        get() {
            return URL
        }

    var ApiKey:String?=null
        get() {
            return API_KEY
        }
}