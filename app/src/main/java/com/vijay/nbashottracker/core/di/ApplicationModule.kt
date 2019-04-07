package com.vijay.nbashottracker.core.di

import android.content.Context
import com.vijay.nbashottracker.ShotTrackerApplication
import com.vijay.nbashottracker.core.schedulers.ISchedulerProvider
import com.vijay.nbashottracker.core.schedulers.SchedulerProvider
import com.vijay.nbashottracker.services.NBAStatsRepository
import com.vijay.nbashottracker.services.Network
import com.vijay.nbashottracker.feature.games.state.AppState
import com.vijay.nbashottracker.feature.games.state.IAppState
import com.vijay.nbashottracker.configuration.API_KEY
import com.vijay.nbashottracker.configuration.GlobalState
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Singleton

@Module
class ApplicationModule(private val application:ShotTrackerApplication){
    @Provides @Singleton fun provideApplicationContext():Context = application

    @Provides @Singleton fun provideNBAStatsRepository(nbaStatsRepository:Network):NBAStatsRepository = nbaStatsRepository

    @Provides @Singleton fun provideSchedulerProvider():ISchedulerProvider = SchedulerProvider()

    @Provides @Singleton fun provideAppState(): IAppState = AppState()

    @Provides @Singleton fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(GlobalState.BaseURL)
            .client(getHttpClient())
            .build()
    }

    private fun getHttpClient():OkHttpClient{
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
        return httpClient
    }
}