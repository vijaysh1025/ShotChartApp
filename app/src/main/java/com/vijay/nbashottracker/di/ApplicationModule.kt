package com.vijay.nbashottracker.di

import android.content.Context
import com.vijay.nbashottracker.ShotChartViewModel
import com.vijay.nbashottracker.ShotTrackerApplication
import com.vijay.nbashottracker.datamodel.DataModel
import com.vijay.nbashottracker.datamodel.IDataModel
import com.vijay.nbashottracker.schedulers.ISchedulerProvider
import com.vijay.nbashottracker.schedulers.SchedulerProvider
import com.vijay.nbashottracker.state.AppState
import com.vijay.nbashottracker.state.IAppState
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application:ShotTrackerApplication){
    @Provides @Singleton fun provideApplicationContext():Context = application

    @Provides @Singleton fun provideUseCase():IDataModel = DataModel()

    @Provides @Singleton fun provideSchedulerProvider():ISchedulerProvider = SchedulerProvider()

    @Provides @Singleton fun provideAppState():IAppState = AppState.instance

    @Provides @Singleton fun provideShotChartViewModel(shotChartViewModel: ShotChartViewModel):ShotChartViewModel = shotChartViewModel
}