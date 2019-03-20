package com.vijay.nbashottracker.datamodel

import com.vijay.nbashottracker.model.NBASportRadarAPI
import com.vijay.nbashottracker.model.playbyplay.*
import com.vijay.nbashottracker.model.APIClient
import com.vijay.nbashottracker.model.dailyschedule.*
import com.vijay.nbashottracker.model.summary.GameSummary
import com.vijay.nbashottracker.model.summary.PlayersItem
import io.reactivex.Single
import io.reactivex.annotations.NonNull
import io.reactivex.schedulers.Schedulers
import retrofit2.create
import java.lang.AssertionError
import java.time.LocalDate

class DataModel : IDataModel{

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
            ?.getScheduleOfDay(localDate.year.toString(), localDate.monthValue.toString(), localDate.dayOfMonth.toString())
            ?.subscribeOn(Schedulers.io())
            ?.map{it: DailySchedule -> it.games as List<Game>}
    }

    @NonNull
    override fun getPlayByPlay(gameId:String):Single<PlayByPlay>?{
        return nbaAPI
            ?.getPlayByPlay(gameId)
            ?.subscribeOn(Schedulers.io())
    }

    @NonNull
    override fun getTeamPlayers(gameId:String, isHomeTeam:Boolean):Single<List<PlayersItem?>>?{
        return nbaAPI
            ?.getGameSummary(gameId)
            ?.subscribeOn(Schedulers.io())
            ?.map{it:GameSummary -> if(isHomeTeam) it.home?.players else it.away?.players}
    }


}