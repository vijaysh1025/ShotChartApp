package com.vijay.nbashottracker.feature.games.usecases


import com.vijay.nbashottracker.core.interactors.UseCase
import com.vijay.nbashottracker.services.NBAStatsRepository
import com.vijay.nbashottracker.feature.games.state.objects.PlayerStats
import javax.inject.Inject

/**
 * Use Case for getting player stats for when a game is clicked in DailyScheduleActivity
 */
class GetPlayerStats
@Inject constructor(private val nbaStatsRepository: NBAStatsRepository):
    UseCase<Map<String, PlayerStats>, GetPlayerStats.Params>(){
    override fun For(params: Params) = nbaStatsRepository.getPlayerStats(params.gameId)
    data class Params(val gameId:String)
}