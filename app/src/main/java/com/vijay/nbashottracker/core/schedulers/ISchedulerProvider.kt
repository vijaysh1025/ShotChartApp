package com.vijay.nbashottracker.core.schedulers


import io.reactivex.Scheduler

/**
 * Allow providing different types of [Scheduler]s.
 */
interface ISchedulerProvider {

    fun computation(): Scheduler

    fun io():Scheduler

    fun ui(): Scheduler
}
