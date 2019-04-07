package com.vijay.nbashottracker.state

import com.vijay.nbashottracker.model.dailyschedule.Game
import com.vijay.nbashottracker.model.playbyplay.PlayByPlay
import com.vijay.nbashottracker.model.playbyplay.Team
import com.vijay.nbashottracker.state.objects.GameItem
import com.vijay.nbashottracker.state.objects.PlayerStats
import io.reactivex.annotations.NonNull
import io.reactivex.subjects.BehaviorSubject
import java.time.LocalDate

enum class TeamType{HOME, AWAY}

interface IAppState {
    val mSelectedDate:BehaviorSubject<LocalDate>
    val mSelectedGame:BehaviorSubject<GameItem>
    val mSelectedPlayer:BehaviorSubject<String>


    val mSelectedTeam:BehaviorSubject<TeamType>
    val mSelectedGamePlayerStats:BehaviorSubject<Map<String,PlayerStats>>
}