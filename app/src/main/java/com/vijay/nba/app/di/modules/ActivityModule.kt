package com.vijay.nba.app.di.modules

import android.app.Activity
import com.vijay.nba.app.di.PerActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(activity: Activity){
    private val activity:Activity = activity

    @Provides @PerActivity fun activity():Activity{
        return this.activity
    }
}