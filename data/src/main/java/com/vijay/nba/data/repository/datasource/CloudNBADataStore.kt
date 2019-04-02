package com.vijay.nba.data.repository.datasource

import com.google.gson.Gson
import com.vijay.nba.data.Configuration
import com.vijay.nba.data.entity.dailyschedule.*
import com.vijay.nba.data.entity.playbyplay.*
import com.vijay.nba.data.entity.summary.*
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


interface CloudNBADataStore{
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

object NBATrackingAPIClient{
    private var _instance:Retrofit? = null
    val instance:Retrofit?
        get(){

            // Handle auto add API Key Parameter to url
            val httpClient = OkHttpClient.Builder().addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original = chain.request()
                    val originalHttpUrl = original.url()

                    val url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api_key", Configuration.ApiKey)
                        .build()

                    // Request customization: add request headers
                    val requestBuilder = original.newBuilder()
                        .url(url)

                    val request = requestBuilder.build()
                    return chain.proceed(request)
                }
            }).build()

            //Create Retrofit client
            if(_instance==null){
                _instance = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Configuration.BaseURL)
                    .client(httpClient)
                    .build()
            }
            return _instance
        }
}
