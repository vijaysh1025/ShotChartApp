package com.vijay.nbashottracker.datamodel

import android.util.EventLog
import com.vijay.nbashottracker.model.NBASportRadarAPI
import com.vijay.nbashottracker.model.playbyplay.*
import com.vijay.nbashottracker.model.APIClient
import com.vijay.nbashottracker.model.dailyschedule.*
import com.vijay.nbashottracker.model.summary.GameSummary
import com.vijay.nbashottracker.model.summary.PlayersItem
import com.vijay.nbashottracker.state.objects.FieldGoalEvent
import com.vijay.nbashottracker.state.objects.PlayerStats
import io.reactivex.Single
import io.reactivex.annotations.NonNull
import io.reactivex.schedulers.Schedulers
import retrofit2.create
import java.lang.AssertionError
import java.time.LocalDate
import javax.inject.Inject


class DataModel
    @Inject constructor(): IDataModel{

    private var _nbaAPI:NBASportRadarAPI?=null
    val nbaAPI:NBASportRadarAPI
    get() {
        if(_nbaAPI == null)
            _nbaAPI = APIClient.instance!!.create<NBASportRadarAPI>()
        return _nbaAPI?:throw AssertionError("Still is null")
    }

    @NonNull
    override fun getGames(localDate:LocalDate): Single<List<Game>>? {
        return nbaAPI
            .getScheduleOfDay(localDate.year.toString(), localDate.monthValue.toString(), localDate.dayOfMonth.toString())
            .subscribeOn(Schedulers.io())
            .map{it.games as? List<Game>}
    }

    @NonNull
    override fun getGameEvents(gameId:String):Single<List<EventsItem?>?>{
        return nbaAPI
            .getPlayByPlay(gameId)
            .subscribeOn(Schedulers.io())
            .map { t->combineEvents(t.periods) }
    }

    override fun getFieldFoalEvents(gameId:String):Single<List<EventsItem?>?>{
        return getGameEvents(gameId)
            .flatMap { it-> Single.just(it.filter { i->i?.eventType!!.contains("point") } )}
    }

    @NonNull
    override fun getTeamPlayers(gameId:String, isHomeTeam:Boolean):Single<List<PlayersItem?>>?{
        return nbaAPI
            .getGameSummary(gameId)
            .subscribeOn(Schedulers.io())
            .map{it:GameSummary -> if(isHomeTeam) it.home?.players else it.away?.players}
    }

    private fun combineEvents(periods:List<PeriodsItem?>?):List<EventsItem?>?{
        val events = periods?.map{ it->it?.events}?.flatMap { it!!.asIterable() }
        return events
    }

    override fun getPlayerStats(gameId: String):Single<Map<String,PlayerStats>>{
        return getFieldFoalEvents(gameId)
            .flatMap {
                Single.just(getPlayerStatsMap(it))
            }
    }

    private fun getPlayerStatsMap(eventList:List<EventsItem?>):MutableMap<String,PlayerStats>{
        var playerStats:MutableMap<String,PlayerStats> = mutableMapOf()
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