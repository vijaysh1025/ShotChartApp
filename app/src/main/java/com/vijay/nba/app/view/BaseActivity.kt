package com.vijay.nba.app.view

import android.app.Activity
import android.os.Bundle
import com.vijay.nba.app.ShotChartApplication
import com.vijay.nba.app.di.components.ApplicationComponent
import com.vijay.nba.app.di.modules.ActivityModule

abstract class BaseActivity: Activity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.getApplicationComponent().inject(this)
    }

    /**
     * Main App from DI
     */
    protected fun getApplicationComponent():ApplicationComponent{
        return (application as ShotChartApplication).getApplicationComponent()
    }

    /**
     * Get Activity Module for DI
     */
    protected fun getActivityModule():ActivityModule{
        return ActivityModule(this)
    }
}