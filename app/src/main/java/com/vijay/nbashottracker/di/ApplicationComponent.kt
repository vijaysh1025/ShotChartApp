package com.vijay.nbashottracker.di

import com.vijay.nbashottracker.ShotTrackerApplication
import com.vijay.nbashottracker.ui.DailyScheduleActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent{
    fun inject(application: ShotTrackerApplication)
    fun inject(dailyScheduleActivity: DailyScheduleActivity)

}