package com.vijay.nbashottracker

import android.app.Application
import com.vijay.nbashottracker.datamodel.DataModel
import io.reactivex.annotations.NonNull
import com.vijay.nbashottracker.datamodel.IDataModel
import com.vijay.nbashottracker.di.ApplicationComponent
import com.vijay.nbashottracker.di.ApplicationModule
import com.vijay.nbashottracker.di.DaggerApplicationComponent
import com.vijay.nbashottracker.schedulers.ISchedulerProvider
import com.vijay.nbashottracker.schedulers.SchedulerProvider
import com.vijay.nbashottracker.state.AppState
import com.vijay.nbashottracker.state.IAppState

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

    @NonNull
    val mDataModel:IDataModel = DataModel()

}