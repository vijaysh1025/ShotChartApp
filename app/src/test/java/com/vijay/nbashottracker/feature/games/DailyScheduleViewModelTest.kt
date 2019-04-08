package com.vijay.nbashottracker.feature.games

import com.vijay.nbashottracker.core.schedulers.ISchedulerProvider
import com.vijay.nbashottracker.feature.games.model.playbyplay.Player
import com.vijay.nbashottracker.core.schedulers.TestSchedulerProvider
import com.vijay.nbashottracker.feature.games.model.dailyschedule.Game
import com.vijay.nbashottracker.feature.games.model.dailyschedule.Team
import com.vijay.nbashottracker.feature.games.model.dailyschedule.Venue
import com.vijay.nbashottracker.feature.games.state.AppState
import com.vijay.nbashottracker.feature.games.state.IAppState
import com.vijay.nbashottracker.feature.games.state.objects.GameItem
import com.vijay.nbashottracker.feature.games.state.objects.PlayerStats
import com.vijay.nbashottracker.feature.games.usecases.GetGames
import com.vijay.nbashottracker.feature.games.usecases.GetPlayerStats
import com.vijay.nbashottracker.feature.games.viewmodels.DailyScheduleViewModel
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
    private lateinit var mGetGames:GetGames

    @Mock
    private lateinit var mGetPlayerStats: GetPlayerStats

    @Mock
    private lateinit var mAppState: IAppState

    private lateinit var mDailyScheduleViewModel: DailyScheduleViewModel

    private lateinit var mSchedulerProvider:ISchedulerProvider



    @Throws(Exception::class)
    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
       mSchedulerProvider = TestSchedulerProvider()
//        mDailyScheduleViewModel = DailyScheduleViewModel(
//            mDataModel!!,
//            mSchedulerProvider!!,
//            mAppState!!
//        )
        mDailyScheduleViewModel = DailyScheduleViewModel(mGetGames,mGetPlayerStats, mSchedulerProvider, mAppState)

    }

    @Test
    fun testGetGames_emitsCorrectGames_whenDateSet(){
        val date:LocalDate = LocalDate.of(2017,12,25)
        val game1: GameItem =
            GameItem(
                Game(
                    "1",
                    Venue("1", "TestArena"),
                    Team("0", "home", "H"),
                    Team("1", "Away", "A")
                )
            )
        val game2: GameItem =
            GameItem(
                Game(
                    "2",
                    Venue("2", "TestArena"),
                    Team("2", "home1", "H1"),
                    Team("3", "Away2", "A2")
                )
            )
        val games:List<GameItem> = listOf(game1,game2)
        Mockito.`when`(mGetGames.For(GetGames.Params(date))).thenReturn(Single.just(games))
        Mockito.`when`(mAppState.mSelectedDate).thenReturn(BehaviorSubject.createDefault(date))

        val testObserver= TestObserver<List<GameItem>>()

        mDailyScheduleViewModel.getGames().subscribeOn(mSchedulerProvider.computation()).subscribe(testObserver)
        mDailyScheduleViewModel.dateSelected(date)

        testObserver.assertNoErrors()

        testObserver.awaitDone(5,TimeUnit.SECONDS).assertValue(games)
    }

    @Test
    fun testGetPlayerStats_emitsCorrectStats_whenGameSelected(){
        val game = GameItem(Game("000", null, null, null))
        val player1 = PlayerStats(Player())
        val player2 = PlayerStats(Player())
        val playerStats = mapOf("p1" to player1, "p2" to player2)

        Mockito.`when`(mGetPlayerStats.For(GetPlayerStats.Params(game.id))).thenReturn(Single.just(playerStats))
        Mockito.`when`(mAppState.mSelectedGame).thenReturn(BehaviorSubject.createDefault(game))

        val testObserver= TestObserver<Map<String, PlayerStats>>()

        mDailyScheduleViewModel.getPlayerStats().subscribeOn(mSchedulerProvider.computation()).subscribe(testObserver)

        testObserver.assertNoErrors()

        testObserver.awaitDone(5,TimeUnit.SECONDS).assertValue(playerStats)
    }

    @Test
    fun testGetPlayerStats_emitsNothing_whenEmptyGame(){
        val game = AppState.EMPTY_GAME
        val player1 = PlayerStats(Player())
        val player2 = PlayerStats(Player())
        val playerStats = mapOf("p1" to player1, "p2" to player2)

        Mockito.`when`(mGetPlayerStats.For(GetPlayerStats.Params(game.id))).thenReturn(Single.just(playerStats))
        Mockito.`when`(mAppState.mSelectedGame).thenReturn(BehaviorSubject.createDefault(game))

        val testObserver= TestObserver<Map<String, PlayerStats>>()

        mDailyScheduleViewModel.getPlayerStats().subscribeOn(mSchedulerProvider.computation()).subscribe(testObserver)

        testObserver.assertNoErrors()

        testObserver.awaitDone(5,TimeUnit.SECONDS).assertNoValues()
    }

}