package com.vijay.nbashottracker.feature.games.state

import com.vijay.nbashottracker.feature.games.state.objects.GameItem
import com.vijay.nbashottracker.feature.games.state.objects.PlayerStats
import io.reactivex.annotations.NonNull
import io.reactivex.subjects.BehaviorSubject
import java.time.LocalDate

class AppState: com.vijay.nbashottracker.feature.games.state.IAppState {

    @NonNull
    override val mSelectedDate:BehaviorSubject<LocalDate> = BehaviorSubject.createDefault(LocalDate.now().minusDays(1))

    @NonNull
    override val mSelectedGame:BehaviorSubject<GameItem> = BehaviorSubject.create()

    @NonNull
    override val mSelectedPlayer:BehaviorSubject<String> = BehaviorSubject.create()

    @NonNull
    override val mSelectedGamePlayerStats:BehaviorSubject<Map<String, PlayerStats>> = BehaviorSubject.create()

    @NonNull
    override val mSelectedTeam:BehaviorSubject<com.vijay.nbashottracker.feature.games.state.TeamType> = BehaviorSubject.createDefault(
        com.vijay.nbashottracker.feature.games.state.TeamType.HOME
    )

    companion object {
        val EMPTY_GAME: GameItem =
            GameItem()
        val EMPTY_STATS = mapOf<String, PlayerStats>()
    }

}
