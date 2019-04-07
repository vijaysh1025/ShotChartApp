package com.vijay.nbashottracker.services

import com.vijay.nbashottracker.feature.games.model.dailyschedule.Game
import com.vijay.nbashottracker.feature.games.model.playbyplay.EventsItem
import com.vijay.nbashottracker.feature.games.model.playbyplay.PeriodsItem
import com.vijay.nbashottracker.feature.games.model.summary.GameSummary
import com.vijay.nbashottracker.feature.games.model.summary.Player
import com.vijay.nbashottracker.feature.games.state.objects.FieldGoalEvent
import com.vijay.nbashottracker.feature.games.state.objects.GameItem
import com.vijay.nbashottracker.feature.games.state.objects.PlayerItem
import com.vijay.nbashottracker.feature.games.state.objects.PlayerStats
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.time.LocalDate
import javax.inject.Inject

interface NBAStatsRepository{
    fun getGames(localDate: LocalDate): Single<List<GameItem>>
    fun getTeamPlayers(gameId:String, isHomeTeam:Boolean):Single<List<PlayerItem>>
    fun getPlayerStats(gameId:String):Single<Map<String,PlayerStats>>
}

class Network
@Inject constructor(private val service:NBASportRadarService):NBAStatsRepository{

    override fun getGames(localDate:LocalDate): Single<List<GameItem>> {
        return service
            .getScheduleOfDay(localDate.year.toString(), localDate.monthValue.toString(), localDate.dayOfMonth.toString())
            .subscribeOn(Schedulers.io())
            .map{it.games.map { game:Game -> GameItem(game) }}
    }


    override fun getTeamPlayers(gameId:String, isHomeTeam:Boolean):Single<List<PlayerItem>>{
        return service
            .getGameSummary(gameId)
            .subscribeOn(Schedulers.io())
            .map{
                if(isHomeTeam)
                    it.home?.players?.map { player:Player-> PlayerItem(player) }
                else
                    it.away?.players?.map{player:Player-> PlayerItem(player)}}
    }

    override fun getPlayerStats(gameId: String):Single<Map<String,PlayerStats>>{
        return getFieldFoalEvents(gameId)
            .flatMap {
                Single.just(getPlayerStatsMap(it))
            }
    }

    private fun getGameEvents(gameId:String):Single<List<EventsItem?>?>{
        return service
            .getPlayByPlay(gameId)
            .subscribeOn(Schedulers.io())
            .map { t->combineEvents(t.periods) }
    }

    private fun getFieldFoalEvents(gameId:String):Single<List<EventsItem?>?>{
        return getGameEvents(gameId)
            .flatMap { it-> Single.just(it.filter { i->i?.eventType!!.contains("point") } )}
    }

    private fun combineEvents(periods:List<PeriodsItem?>?):List<EventsItem?>?{
        val events = periods?.map{ it->it?.events}?.flatMap { it!!.asIterable() }
        return events
    }

    private fun getPlayerStatsMap(eventList:List<EventsItem?>):MutableMap<String,PlayerStats>{
        val playerStats:MutableMap<String,PlayerStats> = mutableMapOf()
        for(e in eventList){
            val player = e?.statistics?.find { it?.type =="fieldgoal" }?.player
            if(player!=null)
            {
                if(!playerStats.containsKey(player.id))
                    playerStats.put(player.id!!, PlayerStats(player))

                playerStats[player.id!!]?.fieldGoalEvents?.add(FieldGoalEvent(e))
            }
        }
        return playerStats
    }
}
