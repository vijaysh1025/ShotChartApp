package com.vijay.nbashottracker.services

import com.vijay.nbashottracker.core.schedulers.ISchedulerProvider
import com.vijay.nbashottracker.core.schedulers.SchedulerProvider
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

/**
 * Repository for converting raw api request data to use case relevant data
 */
class Network
@Inject constructor(private val service:NBASportRadarService, private val schedulerProvider: ISchedulerProvider):NBAStatsRepository{

    // Retrieve raw game data and convert to use case game item data
    override fun getGames(localDate:LocalDate): Single<List<GameItem>> {
        return service
            .getScheduleOfDay(localDate.year.toString(), localDate.monthValue.toString(), localDate.dayOfMonth.toString())
            .subscribeOn(schedulerProvider.io())
            .map{it.games.map { game:Game -> GameItem(game) }}
    }

    // Retrieve raw game summary data and convert to use case team players data
    override fun getTeamPlayers(gameId:String, isHomeTeam:Boolean):Single<List<PlayerItem>>{
        return service
            .getGameSummary(gameId)
            .subscribeOn(schedulerProvider.io())
            .map{
                if(isHomeTeam)
                    it.home?.players?.map { player:Player-> PlayerItem(player) }
                else
                    it.away?.players?.map{player:Player-> PlayerItem(player)}}
    }


    // Retrieve raw play by play data and convert to use case player stats data
    override fun getPlayerStats(gameId: String):Single<Map<String,PlayerStats>>{
        return getFieldFoalEvents(gameId)
            .flatMap {
                Single.just(getPlayerStatsMap(it))
            }
    }

    // Get game events list for all periods in a game
    private fun getGameEvents(gameId:String):Single<List<EventsItem?>?>{
        return service
            .getPlayByPlay(gameId)
            .subscribeOn(schedulerProvider.io())
            .map { t->combineEvents(t.periods) }
    }

    // Get only Field goal events from list of all events
    private fun getFieldFoalEvents(gameId:String):Single<List<EventsItem?>?>{
        return getGameEvents(gameId)
            .flatMap { it-> Single.just(it.filter { i->i?.eventType!!.contains("point") } )}
    }

    // combine events from all periods
    private fun combineEvents(periods:List<PeriodsItem?>?):List<EventsItem?>?{
        val events = periods?.map{ it->it?.events}?.flatMap { it!!.asIterable() }
        return events
    }

    // map raw data to player stats data with player ids as key.
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
