package com.vijay.nbashottracker

import android.app.Application
import com.vijay.nbashottracker.datamodel.DataModel
import io.reactivex.annotations.NonNull
import com.vijay.nbashottracker.datamodel.IDataModel
import com.vijay.nbashottracker.schedulers.ISchedulerProvider
import com.vijay.nbashottracker.schedulers.SchedulerProvider

class ShotTrackerApplication : Application(){
    @NonNull
    val mDataModel:IDataModel = DataModel()

    @NonNull
    fun getDataModel():IDataModel{return mDataModel;}

    @NonNull
    fun getSchedulerProvider():ISchedulerProvider?{
        return SchedulerProvider.instance
    }

    @NonNull
    fun getDailyScheduleViewModel():DailyScheduleViewModel{
        return DailyScheduleViewModel(getDataModel(), getSchedulerProvider())
    }

}