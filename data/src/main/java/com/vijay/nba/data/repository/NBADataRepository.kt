package com.vijay.nba.data.repository
import com.vijay.nba.data.entity.dailyschedule.DailySchedule
import com.vijay.nba.data.repository.datasource.APIClient
import com.vijay.nba.data.repository.datasource.CloudNBADataStore
import com.vijay.nba.domain.model.Game
import com.vijay.nba.domain.model.Team
import com.vijay.nba.domain.model.Venue
import com.vijay.nba.domain.repository.NBARepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.create
import java.lang.AssertionError
import java.time.LocalDate

class NBADataRepository : NBARepository {

    private var _nbaAPI: CloudNBADataStore?=null
    val nbaAPI:CloudNBADataStore
        get() {
            if(_nbaAPI == null)
                _nbaAPI = APIClient.instance!!.create<CloudNBADataStore>()
            return _nbaAPI?:throw AssertionError("Still is null")
        }

    override fun getGames(localDate: LocalDate): Single<List<Game>>? {
        return nbaAPI
            .getScheduleOfDay(localDate.year.toString(), localDate.monthValue.toString(), localDate.dayOfMonth.toString())
            .subscribeOn(Schedulers.io())
            .map{it.games.map {g:com.vijay.nba.data.entity.dailyschedule.Game->
                Game(
                    g.id,
                    Venue(g.venue?.id, g.venue?.name),
                    Team(g.home?.id,g.home?.name,g.home?.alias),
                    Team(g.away?.id,g.away?.name,g.away?.alias)
                )
            }}
    }
}