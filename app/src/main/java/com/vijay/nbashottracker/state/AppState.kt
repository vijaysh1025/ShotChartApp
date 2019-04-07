package com.vijay.nbashottracker.state

import android.graphics.Point
import com.vijay.nbashottracker.model.dailyschedule.Game
import com.vijay.nbashottracker.state.objects.GameItem
import com.vijay.nbashottracker.state.objects.PlayerStats
import io.reactivex.annotations.NonNull
import io.reactivex.subjects.BehaviorSubject
import java.time.LocalDate

class AppState:IAppState{

    @NonNull
    override val mSelectedDate:BehaviorSubject<LocalDate> = BehaviorSubject.createDefault(LocalDate.now().minusDays(1))

    @NonNull
    override val mSelectedGame:BehaviorSubject<GameItem> = BehaviorSubject.create()

    @NonNull
    override val mSelectedPlayer:BehaviorSubject<String> = BehaviorSubject.create()

    @NonNull
    override val mSelectedGamePlayerStats:BehaviorSubject<Map<String,PlayerStats>> = BehaviorSubject.create()

    @NonNull
    override val mSelectedTeam:BehaviorSubject<TeamType> = BehaviorSubject.createDefault(TeamType.HOME)

    companion object {
        val EMPTY_GAME:GameItem = GameItem()
        val EMPTY_STATS = mapOf<String,PlayerStats>()
    }

}
