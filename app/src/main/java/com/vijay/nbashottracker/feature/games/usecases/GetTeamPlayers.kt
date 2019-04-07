package com.vijay.nbashottracker.feature.games.usecases

import com.vijay.nbashottracker.core.interactors.UseCase
import com.vijay.nbashottracker.services.NBAStatsRepository
import com.vijay.nbashottracker.feature.games.state.objects.PlayerItem
import io.reactivex.Single


import javax.inject.Inject

class GetTeamPlayers
@Inject constructor(private val nbaStatsRepository: NBAStatsRepository):
    UseCase<List<PlayerItem>, GetTeamPlayers.Params>(){
    override fun For(params: Params): Single<List<PlayerItem>> = nbaStatsRepository.getTeamPlayers(params.gameId,params.isHomeTeam)
    data class Params(val gameId:String, val isHomeTeam:Boolean)
}