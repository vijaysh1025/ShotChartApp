package com.vijay.nbashottracker.feature.games.state

import com.vijay.nbashottracker.feature.games.state.objects.GameItem
import com.vijay.nbashottracker.feature.games.state.objects.PlayerStats
import io.reactivex.annotations.NonNull
import io.reactivex.subjects.BehaviorSubject
import java.time.LocalDate

/**
 * Singleton (As defined in Dagger Application Module) that holds the state information for the app
 * Used by all view models to emit new states and read previous states.
 */
class AppState: com.vijay.nbashottracker.feature.games.state.IAppState {

    /**
     * Locale Date State that determines which games to display.
     */
    @NonNull
    override val mSelectedDate:BehaviorSubject<LocalDate> = BehaviorSubject.createDefault(LocalDate.now().minusDays(1))

    /**
     * Current selected GameItem for displaying Game info in ShotChartActivity.
     * Set to AppState.EMPTY_GAME when in DailyScheduleActivity
     */
    @NonNull
    override val mSelectedGame:BehaviorSubject<GameItem> = BehaviorSubject.create()

    /**
     * Current Player determines which players shot chart should be shown.
     */
    @NonNull
    override val mSelectedPlayer:BehaviorSubject<String> = BehaviorSubject.create()

    /**
     * Player Stats data for each player id (Map Key) that is loaded when a game is selected
     */
    @NonNull
    override val mSelectedGamePlayerStats:BehaviorSubject<Map<String, PlayerStats>> = BehaviorSubject.create()

    /**
     * Current Selected Team (Home / Away Team) -> updates list of selectable players
     */
    @NonNull
    override val mSelectedTeam:BehaviorSubject<com.vijay.nbashottracker.feature.games.state.TeamType> = BehaviorSubject.createDefault(
        com.vijay.nbashottracker.feature.games.state.TeamType.HOME
    )

    /**
     * Static fields for reseting Game and Stats state.
     */
    companion object {
        val EMPTY_GAME: GameItem =
            GameItem()
        val EMPTY_STATS = mapOf<String, PlayerStats>()
    }

}
