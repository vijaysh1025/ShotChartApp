package com.vijay.nbashottracker.state

import com.vijay.nbashottracker.model.dailyschedule.Game
import com.vijay.nbashottracker.model.playbyplay.PlayByPlay
import com.vijay.nbashottracker.model.playbyplay.Team
import io.reactivex.annotations.NonNull
import io.reactivex.subjects.BehaviorSubject
import java.time.LocalDate

enum class TeamType{HOME, AWAY}

interface IAppState {
    val mSelectedDate:BehaviorSubject<LocalDate>
    val mSelectedGame:BehaviorSubject<Game>


    val mSelectedTeam:BehaviorSubject<TeamType>
    val mSelectedGamePlayByPlay:BehaviorSubject<PlayByPlay>

}