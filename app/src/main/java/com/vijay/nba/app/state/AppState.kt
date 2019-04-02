package com.vijay.nba.app.state

import android.graphics.Point
import com.vijay.nba.app.model.GameItemModel
import com.vijay.nbashottracker.model.dailyschedule.Game
import com.vijay.nbashottracker.state.objects.PlayerStats
import io.reactivex.annotations.NonNull
import io.reactivex.subjects.BehaviorSubject
import java.time.LocalDate

class AppState private constructor():IAppState{

    /**
     * Stream that binds to current date set by user.
     * Date defaults to today's date
     */
    @NonNull
    override val mSelectedDate:BehaviorSubject<LocalDate> = BehaviorSubject.createDefault(LocalDate.now().minusDays(1))

    /**
     * Stream that binds to the current game selected by user
     * Set to AppState.EMPTY_GAME when exiting detail view
     */
    @NonNull
    override val mSelectedGame:BehaviorSubject<GameItemModel> = BehaviorSubject.create()

    /**
     * Stream that binds to current playerId selected
     */
    @NonNull
    override val mSelectedPlayer:BehaviorSubject<String> = BehaviorSubject.create()

    /**
     * Stream that emits a map of player ids and player stats when a game is selected.
     * Used to load player data and player shot chart
     */
    @NonNull
    override val mSelectedGamePlayerStats:BehaviorSubject<Map<String,PlayerStats>> = BehaviorSubject.create()

    /**
     * Stream that binds to home / away team buttons on the detail view. Also, updates
     * player list.
     */
    @NonNull
    override val mSelectedTeam:BehaviorSubject<TeamType> = BehaviorSubject.createDefault(TeamType.HOME)

    /**
     * Stream of shot states observes mSelectedPlayer and updates shot view accordingly
     */
    @NonNull
    override val mPlayerShots:BehaviorSubject<List<ShotState>> = BehaviorSubject.create()

    /**
     * Singleton state implementation
     */
    companion object {
        private var INSTANCE: IAppState? = null

        //Use this to reset the mSelectedGame BehaviorSubject so that the ShotChart activity is not automatically triggered
        val EMPTY_GAME:Game = Game("",null,null,null)

        val EMPTY_STATS:Map<String,PlayerStats> = mapOf()

        val instance: IAppState
            get() {
                if (INSTANCE == null) {
                    INSTANCE = AppState()
                }
                return INSTANCE!!
            }
    }
}

class ShotState
constructor(_pos:Point, _make:Boolean){
    val pos:Point = _pos
    val make:Boolean = _make
}