package com.vijay.nbashottracker.state

import com.vijay.nbashottracker.model.dailyschedule.Game
import io.reactivex.annotations.NonNull
import io.reactivex.subjects.BehaviorSubject
import java.time.LocalDate

interface IAppState {
    val mSelectedDate:BehaviorSubject<LocalDate>
    val mSelectedGame:BehaviorSubject<Game>
}