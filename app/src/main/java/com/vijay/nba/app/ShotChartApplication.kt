package com.vijay.nba.app

import android.app.Application
import com.vijay.nba.app.di.components.ApplicationComponent
import com.vijay.nba.app.di.components.DaggerApplicationComponent
import com.vijay.nba.app.di.modules.ApplicationModule
import dagger.android.DaggerApplication

class ShotChartApplication : Application() {
    private var applicationComponent:ApplicationComponent?=null

    override fun onCreate() {
        super.onCreate()
        this.initizlizeInjector()
    }

    private fun initizlizeInjector(){
        this.applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    fun getApplicationComponent():ApplicationComponent{
        return this.applicationComponent!!
    }
}