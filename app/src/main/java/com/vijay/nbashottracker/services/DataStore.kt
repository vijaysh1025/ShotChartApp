package com.vijay.nbashottracker.model

import com.vijay.nbashottracker.utilities.*
import retrofit2.http.GET
import io.reactivex.Observable;
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.vijay.nbashottracker.utilities.*
import io.reactivex.Single
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.http.Path
import java.io.IOException


interface DataStore{

    @GET("/nba/trial/v5/en/games/{year}/{month}/{day}/schedule.json")
    fun getScheduleOfDay(
        @Path("year") year:String,
        @Path("month") month:String,
        @Path("day") day:String
    ): Single<DailySchedule>
}

object APIClient{
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
                        .addQueryParameter("api_key", API_KEY)
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
                    .baseUrl(BASE_URL)
                    .client(httpClient)
                    .build()
            }
            return _instance
        }
}
