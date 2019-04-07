package com.vijay.nbashottracker.usecases


import com.vijay.nbashottracker.core.interactors.UseCase
import com.vijay.nbashottracker.services.NBAStatsRepository
import com.vijay.nbashottracker.state.objects.PlayerStats
import javax.inject.Inject

class GetPlayerStats
@Inject constructor(private val nbaStatsRepository: NBAStatsRepository):
    UseCase<Map<String, PlayerStats>, GetPlayerStats.Params>(){
    override fun For(params:Params) = nbaStatsRepository.getPlayerStats(params.gameId)
    data class Params(val gameId:String)
}