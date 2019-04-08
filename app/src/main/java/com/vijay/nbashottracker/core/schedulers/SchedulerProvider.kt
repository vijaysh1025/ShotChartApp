package com.vijay.nbashottracker.core.schedulers


import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * Provides different types of schedulers.
 */
class SchedulerProvider
@Inject constructor() : ISchedulerProvider {

    /**
     * Used for all non-UI related work
     */
    override fun computation(): Scheduler {
        return Schedulers.computation()
    }

    /**
     * UI Thread for displaying final result to the UI.
     */
    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}
