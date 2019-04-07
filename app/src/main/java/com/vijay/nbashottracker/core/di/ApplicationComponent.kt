package com.vijay.nbashottracker.core.di

import com.vijay.nbashottracker.ShotTrackerApplication
import com.vijay.nbashottracker.feature.games.view.DailyScheduleActivity
import com.vijay.nbashottracker.feature.games.view.ShotChartActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent{
    fun inject(application: ShotTrackerApplication)
    fun inject(dailyScheduleActivity: DailyScheduleActivity)
    fun inject(shotChartActivity: ShotChartActivity)
}