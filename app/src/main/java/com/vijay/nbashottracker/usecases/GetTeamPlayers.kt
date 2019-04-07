package com.vijay.nbashottracker.usecases

import com.vijay.nbashottracker.core.interactors.UseCase
import com.vijay.nbashottracker.model.summary.Player
import com.vijay.nbashottracker.services.NBAStatsRepository
import com.vijay.nbashottracker.state.objects.PlayerItem
import io.reactivex.Single


import javax.inject.Inject

class GetTeamPlayers
@Inject constructor(private val nbaStatsRepository: NBAStatsRepository):
    UseCase<List<PlayerItem>, GetTeamPlayers.Params>(){
    override fun For(params:Params): Single<List<PlayerItem>> = nbaStatsRepository.getTeamPlayers(params.gameId,params.isHomeTeam)
    data class Params(val gameId:String, val isHomeTeam:Boolean)
}