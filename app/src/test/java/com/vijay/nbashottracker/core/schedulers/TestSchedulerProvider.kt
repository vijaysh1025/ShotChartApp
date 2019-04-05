package com.vijay.nbashottracker.core.schedulers


import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


/**
 * Provides different types of schedulers.
 */
class TestSchedulerProvider : ISchedulerProvider {

    override fun computation(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun ui(): Scheduler {
        return Schedulers.trampoline()
    }
}
