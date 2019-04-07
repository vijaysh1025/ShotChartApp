package com.vijay.nbashottracker.configuration

const val DEV = false

const val DEV_URL = "https://www.dropbox.com"
const val BASE_URL ="https://api.sportradar.us"

const val API_KEY = "fkgfzb9terxk7289s6t3u5bf"

object GlobalState {
    var BaseURL:String= BASE_URL
    get() {
        if(DEV)
            return DEV_URL
        else
            return BASE_URL
    }
}