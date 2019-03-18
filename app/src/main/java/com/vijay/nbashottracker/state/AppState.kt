package com.vijay.nbashottracker.state

import com.vijay.nbashottracker.model.dailyschedule.Game
import com.vijay.nbashottracker.model.playbyplay.PlayByPlay
import com.vijay.nbashottracker.model.playbyplay.Team
import com.vijay.nbashottracker.schedulers.ISchedulerProvider
import com.vijay.nbashottracker.schedulers.SchedulerProvider
import io.reactivex.annotations.NonNull
import io.reactivex.subjects.BehaviorSubject
import java.time.LocalDate

class AppState private constructor():IAppState{


    @NonNull
    override val mSelectedDate:BehaviorSubject<LocalDate> = BehaviorSubject.createDefault(LocalDate.now())

    @NonNull
    override val mSelectedGame:BehaviorSubject<Game> = BehaviorSubject.create()

    @NonNull
    override val mSelectedGamePlayByPlay:BehaviorSubject<PlayByPlay> = BehaviorSubject.create()

    @NonNull
    override val mSelectedTeam:BehaviorSubject<TeamType> = BehaviorSubject.create()

    companion object {
        private var INSTANCE: IAppState? = null

        val instance: IAppState?
            get() {
                if (INSTANCE == null) {
                    INSTANCE = AppState()
                }
                return INSTANCE
            }
    }
}