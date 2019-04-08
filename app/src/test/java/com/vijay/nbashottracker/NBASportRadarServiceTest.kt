package com.vijay.nbashottracker


import com.vijay.nbashottracker.feature.games.model.dailyschedule.DailySchedule

import com.vijay.nbashottracker.feature.games.model.dailyschedule.*
import com.vijay.nbashottracker.feature.games.model.playbyplay.*
import com.vijay.nbashottracker.feature.games.model.summary.GameSummary
import com.vijay.nbashottracker.core.schedulers.TestSchedulerProvider
import com.vijay.nbashottracker.feature.games.model.dailyschedule.Game
import com.vijay.nbashottracker.feature.games.model.playbyplay.EventsItem
import com.vijay.nbashottracker.feature.games.state.objects.PlayerStats
import com.vijay.nbashottracker.services.NBASportRadarService
import com.vijay.nbashottracker.services.NBAStatsRepository
import com.vijay.nbashottracker.services.Network
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.time.LocalDate
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class NBASportRadarServiceTest{

    private lateinit var mSchedulerProvider:TestSchedulerProvider

    private lateinit var mNetworkRepository:NBAStatsRepository

    @Mock
    private lateinit var mNBASportRadarService: NBASportRadarService

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        mSchedulerProvider = TestSchedulerProvider()
        mNetworkRepository = Network(mNBASportRadarService)
    }

/*    @Test
    fun getScheduleOfDayTest(){
        val testObserver = TestObserver<com.vijay.nbashottracker.feature.games.model.dailyschedule.DailySchedule>()
        val testScheduler = TestScheduler()
        val date = LocalDate.parse("2015-12-25")
        val datePath = "${date.year} ${date.monthValue} ${date.dayOfMonth}"
        System.out.println(datePath)
        var disposable = APIClient.instance
            ?.create<NBASportRadarAPI>()
            ?.getScheduleOfDay(date.year.toString(), date.monthValue.toString(), date.dayOfMonth.toString())
            ?.subscribeOn(testScheduler)
            ?.subscribe(testObserver)

        testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)

        testObserver.assertValue { t->(t.games as List<com.vijay.nbashottracker.feature.games.model.dailyschedule.Game>).get(0).id == "b55c5579-950b-4726-8d36-6467f6caa772" }
    }


    @Test
    fun getPlayerList(){
        val testObserver = TestObserver<com.vijay.nbashottracker.feature.games.model.summary.GameSummary>()
        val testScheduler = TestScheduler()
        val gameId = "013dd2a7-fec4-4cc5-b819-f3cf16a1f820"

        var disposable = APIClient.instance
            ?.create<NBASportRadarAPI>()
            ?.getGameSummary(gameId)
            ?.subscribeOn(testScheduler)
            ?.subscribe(testObserver)

        testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)

        testObserver.assertValue { t->t.attendance == 19129}

    }

    @Test
    fun testGetFieldGoalEvents_GivenGame(){
        val testObserver = TestObserver<List<com.vijay.nbashottracker.feature.games.model.playbyplay.EventsItem?>>()
        val testScheduler = TestScheduler()
        val gameId = "013dd2a7-fec4-4cc5-b819-f3cf16a1f820"

        val disposable = mDataModel
            ?.getFieldFoalEvents(gameId)
            ?.subscribeOn(mSchedulerProvider?.computation())
            ?.subscribe(testObserver)

        testObserver.assertNoErrors()

        testObserver.awaitDone(10,TimeUnit.SECONDS).assertValue{
           it.all { t->t?.eventType!!.contains("point") }
        }
    }

    @Test
    fun testGetPlayerStatsMap_GivenGame(){
        val testObserver = TestObserver<Map<String, PlayerStats>>()
        val testScheduler = TestScheduler()
        val gameId = "013dd2a7-fec4-4cc5-b819-f3cf16a1f820"

        val disposable = mDataModel
            ?.getPlayerStats(gameId)
            ?.subscribeOn(mSchedulerProvider?.computation())
            ?.subscribe(testObserver)

        testObserver.assertNoErrors()

        testObserver.awaitDone(5,TimeUnit.SECONDS).assertValue{
            it.isNotEmpty()
        }
    }

    @After
    fun tearDown(){
        mDataModel = null
        mSchedulerProvider = null
    }*/
}