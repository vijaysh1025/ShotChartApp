package com.vijay.nbashottracker

import android.app.Application
import com.vijay.nbashottracker.core.di.ApplicationComponent
import com.vijay.nbashottracker.core.di.ApplicationModule
import com.vijay.nbashottracker.core.di.DaggerApplicationComponent

class ShotTrackerApplication : Application(){

    val appComponent:ApplicationComponent by lazy(mode=LazyThreadSafetyMode.NONE){
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
    }

    private fun injectMembers()=appComponent.inject(this)

}