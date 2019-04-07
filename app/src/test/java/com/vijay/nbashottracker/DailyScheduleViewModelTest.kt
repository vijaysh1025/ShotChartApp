package com.vijay.nbashottracker

import com.vijay.nbashottracker.model.dailyschedule.*
import com.vijay.nbashottracker.model.playbyplay.Player
import com.vijay.nbashottracker.core.schedulers.TestSchedulerProvider
import com.vijay.nbashottracker.state.AppState
import com.vijay.nbashottracker.state.IAppState
import com.vijay.nbashottracker.state.objects.PlayerStats
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.BehaviorSubject
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.lang.Exception
import java.time.LocalDate
import java.util.concurrent.TimeUnit

class DailyScheduleViewModelTest{
    @Mock
    private var mDataModel:IDataModel?=null

    private var mDailyScheduleViewModel:DailyScheduleViewModel?=null

    private var mSchedulerProvider:TestSchedulerProvider? = null

    @Mock
    private var mAppState:IAppState?=null

    @Throws(Exception::class)
    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        mSchedulerProvider = TestSchedulerProvider()
        mDailyScheduleViewModel = DailyScheduleViewModel(mDataModel!!, mSchedulerProvider!!,mAppState!!)
    }

    @Test
    fun testGetGames_emitsCorrectGames_whenDateSet(){
        val date:LocalDate = LocalDate.of(2017,12,25)
        val game1: Game = Game(
            "1",
            Venue("1", "TestArena"),
            Team("0", "home", "H"),
            Team("1", "Away", "A")
        )
        val game2: Game = Game(
            "2",
            Venue("2", "TestArena"),
            Team("2", "home1", "H1"),
            Team("3", "Away2", "A2")
        )
        val games:List<Game> = listOf(game1,game2)
        Mockito.`when`(mDataModel?.getGames(date)).thenReturn(Single.just(games))
        Mockito.`when`(mAppState?.mSelectedDate).thenReturn(BehaviorSubject.createDefault(date))

        var testObserver= TestObserver<List<Game>>()

        mDailyScheduleViewModel!!.getGames().subscribeOn(mSchedulerProvider?.computation()).subscribe(testObserver)
        mDailyScheduleViewModel!!.dateSelected(date)

        testObserver.assertNoErrors()

        testObserver.awaitDone(5,TimeUnit.SECONDS).assertValue(games)
    }

    @Test
    fun testGetPlayerStats_emitsCorrectStats_whenGameSelected(){
        val game = Game("000",null,null,null)
        val player1 = PlayerStats(Player())
        val player2 = PlayerStats(Player())
        val playerStats = mapOf("p1" to player1, "p2" to player2)

        Mockito.`when`(mDataModel?.getPlayerStats(game.id)).thenReturn(Single.just(playerStats))
        Mockito.`when`(mAppState?.mSelectedGame).thenReturn(BehaviorSubject.createDefault(game))

        var testObserver= TestObserver<Map<String,PlayerStats>>()

        mDailyScheduleViewModel!!.getPlayerStats().subscribeOn(mSchedulerProvider?.computation()).subscribe(testObserver)

        testObserver.assertNoErrors()

        testObserver.awaitDone(5,TimeUnit.SECONDS).assertValue(playerStats)
    }

    @Test
    fun testGetPlayerStats_emitsNothing_whenEmptyGame(){
        val game = AppState.EMPTY_GAME
        val player1 = PlayerStats(Player())
        val player2 = PlayerStats(Player())
        val playerStats = mapOf("p1" to player1, "p2" to player2)

        Mockito.`when`(mDataModel?.getPlayerStats(game.id)).thenReturn(Single.just(playerStats))
        Mockito.`when`(mAppState?.mSelectedGame).thenReturn(BehaviorSubject.createDefault(game))

        var testObserver= TestObserver<Map<String,PlayerStats>>()

        mDailyScheduleViewModel!!.getPlayerStats().subscribeOn(mSchedulerProvider?.computation()).subscribe(testObserver)

        testObserver.assertNoErrors()

        testObserver.awaitDone(5,TimeUnit.SECONDS).assertNoValues()
    }

}