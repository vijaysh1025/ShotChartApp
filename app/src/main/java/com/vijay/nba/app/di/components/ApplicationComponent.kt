package com.vijay.nba.app.di.components

import android.content.Context
import com.vijay.nba.app.di.modules.ApplicationModule
import com.vijay.nba.app.view.BaseActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
    interface ApplicationComponent {
    fun inject(baseActivity: BaseActivity)

    fun context(): Context

}