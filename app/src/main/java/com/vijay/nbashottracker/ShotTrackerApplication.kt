package com.vijay.nbashottracker

import android.app.Application
import com.vijay.nbashottracker.datamodel.DataModel
import io.reactivex.annotations.NonNull
import com.vijay.nbashottracker.datamodel.IDataModel
import com.vijay.nbashottracker.schedulers.ISchedulerProvider
import com.vijay.nbashottracker.schedulers.SchedulerProvider
import com.vijay.nbashottracker.state.AppState
import com.vijay.nbashottracker.state.IAppState

class ShotTrackerApplication : Application(){
    @NonNull
    val mDataModel:IDataModel = DataModel()

    @NonNull
    fun getDataModel():IDataModel{return mDataModel;}

    @NonNull
    fun getSchedulerProvider():ISchedulerProvider{
        return SchedulerProvider.instance!!
    }

    @NonNull
    fun getAppState():IAppState{
        return AppState.instance!!
    }

    @NonNull
    fun getDailyScheduleViewModel():DailyScheduleViewModel{
        return DailyScheduleViewModel(getDataModel(), getSchedulerProvider(), getAppState())
    }

}