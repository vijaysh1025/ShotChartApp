package com.vijay.nbashottracker


import com.vijay.nbashottracker.feature.games.model.dailyschedule.DailySchedule

import com.vijay.nbashottracker.feature.games.model.dailyschedule.*
import com.vijay.nbashottracker.feature.games.model.playbyplay.*
import com.vijay.nbashottracker.feature.games.model.summary.GameSummary
import com.vijay.nbashottracker.core.schedulers.TestSchedulerProvider
import com.vijay.nbashottracker.feature.games.model.dailyschedule.Game
import com.vijay.nbashottracker.feature.games.model.dailyschedule.Team
import com.vijay.nbashottracker.feature.games.model.playbyplay.EventsItem
import com.vijay.nbashottracker.feature.games.state.objects.GameItem
import com.vijay.nbashottracker.feature.games.state.objects.PlayerStats
import com.vijay.nbashottracker.services.NBASportRadarService
import com.vijay.nbashottracker.services.NBAStatsRepository
import com.vijay.nbashottracker.services.Network
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.time.LocalDate
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class NBAStatsRepositoryTest{

    private lateinit var mSchedulerProvider:TestSchedulerProvider

    private lateinit var mNetworkRepository:NBAStatsRepository

    @Mock
    private lateinit var mNBASportRadarService: NBASportRadarService

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        mSchedulerProvider = TestSchedulerProvider()
        mNetworkRepository = Network(mNBASportRadarService, mSchedulerProvider)
    }

    @Test
    fun testGetGames_returnsGameItems_whenDateSet(){
        val testObserver = TestObserver<List<GameItem>>()
        val testScheduler = TestScheduler()
        val date = LocalDate.parse("2015-12-25")

        val game1:Game = Game(
            "1",
            Venue("1", "TestArena"),
            Team("0", "home", "H"),
            Team("1", "Away", "A")
        )
        val game2:Game = Game(
            "2",
            Venue("2", "TestArena"),
            Team("2", "home1", "H1"),
            Team("3", "Away2", "A2")
        )

        val games:List<Game> = listOf(game1,game2)

        val gameItems:List<GameItem> = listOf(GameItem(game1), GameItem(game2))

        val dailySchedule:DailySchedule = DailySchedule(games)

        Mockito.`when`(mNBASportRadarService.getScheduleOfDay(date.year.toString(),date.monthValue.toString(),date.dayOfMonth.toString())).thenReturn(Single.just(dailySchedule))

        mNetworkRepository.getGames(date)
            .subscribe(testObserver)

        testObserver.assertNoErrors()
        testObserver.awaitDone(5, TimeUnit.SECONDS).assertValue {
            it[0].id == game1.id && it[1].id==game2.id
        }
    }
}