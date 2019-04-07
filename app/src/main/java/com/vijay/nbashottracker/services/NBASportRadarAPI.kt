package com.vijay.nbashottracker.model

import com.google.gson.Gson
import com.vijay.nbashottracker.feature.games.model.dailyschedule.DailySchedule
import com.vijay.nbashottracker.feature.games.model.playbyplay.PlayByPlay
import com.vijay.nbashottracker.feature.games.model.summary.GameSummary
import com.vijay.nbashottracker.configuration.*
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.http.Path
import java.io.IOException


interface NBASportRadarAPI{
    @GET("/nba/trial/v5/en/games/{year}/{month}/{day}/schedule.json")
    fun getScheduleOfDay(
        @Path("year") year:String,
        @Path("month") month:String,
        @Path("day") day:String
    ): Single<DailySchedule>

    @GET("/nba/trial/v5/en/games/{gameId}/pbp.json")
    fun getPlayByPlay(
      @Path("gameId") gameId:String
    ):Single<PlayByPlay>

    @GET("/nba/trial/v5/en/games/{gameId}/summary.json")
    fun getGameSummary(
        @Path("gameId") gameId:String
    ):Single<GameSummary>
}


