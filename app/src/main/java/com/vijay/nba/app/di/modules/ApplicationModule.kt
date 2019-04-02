package com.vijay.nba.app.di.modules

import android.content.Context
import com.vijay.nba.app.ShotChartApplication
import com.vijay.nba.app.schedulers.ISchedulerProvider
import com.vijay.nba.app.schedulers.SchedulerProvider
import com.vijay.nba.app.state.AppState
import com.vijay.nba.app.state.IAppState
import com.vijay.nba.app.viewmodel.DailyScheduleViewModel
import com.vijay.nba.app.viewmodel.ShotChartViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(application: ShotChartApplication){
    private val application:ShotChartApplication = application

    @Provides @Singleton fun provideApplicationContext(): Context {
        return this.application
    }

    @Provides @Singleton fun provideScheduler():ISchedulerProvider{
        return SchedulerProvider.instance
    }

    @Provides @Singleton fun provideAppState():IAppState{
        return AppState.instance
    }

    @Provides @Singleton fun provideDailyScheduleViewModel(dailyScheduleViewModel: DailyScheduleViewModel):DailyScheduleViewModel{
        return dailyScheduleViewModel
    }

    @Provides @Singleton fun provideShotChartViewModel(shotChartViewModel: ShotChartViewModel):ShotChartViewModel{
        return shotChartViewModel
    }

}