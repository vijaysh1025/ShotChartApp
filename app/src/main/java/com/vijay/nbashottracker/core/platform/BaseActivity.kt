package com.vijay.nbashottracker.core.platform

import android.support.v7.app.AppCompatActivity
import com.vijay.nbashottracker.ShotTrackerApplication
import com.vijay.nbashottracker.core.di.ApplicationComponent
import io.reactivex.annotations.NonNull
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(){
    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as ShotTrackerApplication).appComponent
    }
}