package com.vijay.nbashottracker.usecases

import com.vijay.nbashottracker.core.interactors.UseCase
import com.vijay.nbashottracker.model.dailyschedule.Game
import com.vijay.nbashottracker.services.NBAStatsRepository
import com.vijay.nbashottracker.state.objects.GameItem
import io.reactivex.Single
import java.time.LocalDate
import javax.inject.Inject

class GetGames
@Inject constructor(private val nbaStatsRepository: NBAStatsRepository):
    UseCase<List<GameItem>, GetGames.Params>(){
    override fun For(params:Params) : Single<List<GameItem>> = nbaStatsRepository.getGames(params.localDate)
    data class Params(val localDate: LocalDate)
}