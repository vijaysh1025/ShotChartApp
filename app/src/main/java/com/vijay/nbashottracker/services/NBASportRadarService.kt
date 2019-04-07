package com.vijay.nbashottracker.services

import com.vijay.nbashottracker.model.NBASportRadarAPI
import com.vijay.nbashottracker.feature.games.model.dailyschedule.DailySchedule
import com.vijay.nbashottracker.feature.games.model.playbyplay.PlayByPlay
import com.vijay.nbashottracker.feature.games.model.summary.GameSummary
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Inject

class NBASportRadarService
@Inject constructor(retrofit: Retrofit):NBASportRadarAPI{
    private val nbaApi by lazy { retrofit.create<NBASportRadarAPI>() }

    override fun getScheduleOfDay(year: String, month: String, day: String): Single<DailySchedule> = nbaApi.getScheduleOfDay(year,month,day)
    override fun getGameSummary(gameId: String): Single<GameSummary> = nbaApi.getGameSummary(gameId)
    override fun getPlayByPlay(gameId: String): Single<PlayByPlay> = nbaApi.getPlayByPlay(gameId)

}