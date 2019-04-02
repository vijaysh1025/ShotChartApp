package com.vijay.nba.app.di.components

import android.app.Activity
import com.vijay.nba.app.di.PerActivity
import com.vijay.nba.app.di.modules.ActivityModule
import dagger.Component

@PerActivity
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun activity(): Activity
}