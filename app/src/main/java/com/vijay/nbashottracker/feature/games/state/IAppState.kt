package com.vijay.nbashottracker.feature.games.state

import com.vijay.nbashottracker.feature.games.state.objects.GameItem
import com.vijay.nbashottracker.feature.games.state.objects.PlayerStats
import io.reactivex.subjects.BehaviorSubject
import java.time.LocalDate

enum class TeamType{HOME, AWAY}

interface IAppState {
    val mSelectedDate:BehaviorSubject<LocalDate>
    val mSelectedGame:BehaviorSubject<GameItem>
    val mSelectedPlayer:BehaviorSubject<String>


    val mSelectedTeam:BehaviorSubject<TeamType>
    val mSelectedGamePlayerStats:BehaviorSubject<Map<String, PlayerStats>>
}