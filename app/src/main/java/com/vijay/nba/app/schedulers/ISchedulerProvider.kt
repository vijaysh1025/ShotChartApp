package com.vijay.nba.app.schedulers


import io.reactivex.Scheduler

/**
 * Allow providing different types of [Scheduler]s.
 */
interface ISchedulerProvider {

    fun computation(): Scheduler

    fun ui(): Scheduler
}
