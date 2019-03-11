package com.vijay.nbashottracker.schedulers


import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler


/**
 * Provides different types of schedulers.
 */
class TestSchedulerProvider : ISchedulerProvider {

    override fun computation(): Scheduler {
        return TestScheduler()
    }

    override fun ui(): Scheduler {
        return TestScheduler()
    }
}
