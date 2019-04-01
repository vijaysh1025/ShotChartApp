package com.vijay.nba.domain.repository

import com.vijay.nba.domain.model.Game
import io.reactivex.Single
import io.reactivex.annotations.NonNull
import java.time.LocalDate

interface NBARepository {
    @NonNull
    fun getGames(localDate: LocalDate): Single<List<Game>>?
}