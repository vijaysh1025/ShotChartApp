package com.vijay.nbashottracker.state

import android.graphics.Point
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

    @NonNull
    override val mPlayerShots:BehaviorSubject<List<ShotState>> = BehaviorSubject.create()

    companion object {
        private var INSTANCE: IAppState? = null

        //Use this to reset the mSelectedGame BehaviorSubject so that the ShotChart activity is not automatically triggered
        val EMPTY_GAME:Game = Game("",null,null,null)

        val instance: IAppState?
            get() {
                if (INSTANCE == null) {
                    INSTANCE = AppState()
                }
                return INSTANCE
            }
    }
}

class ShotState
constructor(normalizedPos:Point, make:Boolean)