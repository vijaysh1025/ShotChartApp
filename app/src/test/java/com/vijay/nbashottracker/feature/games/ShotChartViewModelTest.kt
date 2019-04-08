package com.vijay.nbashottracker.feature.games

import com.vijay.nbashottracker.core.schedulers.TestSchedulerProvider
import com.vijay.nbashottracker.feature.games.model.dailyschedule.Game
import com.vijay.nbashottracker.feature.games.model.playbyplay.Player
import com.vijay.nbashottracker.feature.games.state.IAppState
import com.vijay.nbashottracker.feature.games.state.TeamType
import com.vijay.nbashottracker.feature.games.state.objects.GameItem
import com.vijay.nbashottracker.feature.games.state.objects.PlayerItem
import com.vijay.nbashottracker.feature.games.state.objects.PlayerStats
import com.vijay.nbashottracker.feature.games.usecases.GetTeamPlayers
import com.vijay.nbashottracker.feature.games.viewmodels.ShotChartViewModel
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.BehaviorSubject
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.lang.Exception
import java.util.concurrent.TimeUnit

class ShotChartViewModelTest{
    @Mock
    private lateinit var mGetTeamPlayers: GetTeamPlayers

    private lateinit var mShotChartViewModel: ShotChartViewModel

    private lateinit var mSchedulerProvider:TestSchedulerProvider

    @Mock
    private lateinit var mAppState: IAppState

    @Throws(Exception::class)
    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        mSchedulerProvider = TestSchedulerProvider()
        mShotChartViewModel = ShotChartViewModel(
            mGetTeamPlayers,
            mSchedulerProvider,
            mAppState
        )
    }

    @Test
    fun testGetTeamPlayers_emitsPlayerList_whenTeamSelected(){
        val game = GameItem(Game("000", null, null, null))
        val player1 = PlayerItem(com.vijay.nbashottracker.feature.games.model.summary.Player("111", "Test Player",true,"",true,"00","001"))
        val players = listOf(player1)

        Mockito.`when`(mGetTeamPlayers.For(GetTeamPlayers.Params(game.id,true))).thenReturn(Single.just(players))
        Mockito.`when`(mAppState.mSelectedTeam).thenReturn(BehaviorSubject.createDefault(TeamType.HOME))
        Mockito.`when`(mAppState.mSelectedGame).thenReturn(BehaviorSubject.createDefault(game))


        val testObserver= TestObserver<List<PlayerItem>>()

        mShotChartViewModel.getTeamPlayers()?.subscribeOn(mSchedulerProvider.computation())?.subscribe(testObserver)

        testObserver.assertNoErrors()

        testObserver.awaitDone(5,TimeUnit.SECONDS).assertValue(players)

    }

    @Test
    fun testGetPlayerStats_emitsPlayerStats_whenPlayerSelected(){

        val player1Id = "000"
        val player1Stat =
            PlayerStats(
                Player(
                    "000",
                    "Test1 Player",
                    "00",
                    player1Id
                )
            )

        val player2Id = "001"
        val player2Stat =
            PlayerStats(
                Player(
                    "001",
                    "Test2 Player",
                    "01",
                    player2Id
                )
            )

        val playerStats = mapOf(player1Id to player1Stat, player2Id to player2Stat)

        Mockito.`when`(mAppState.mSelectedPlayer).thenReturn(BehaviorSubject.createDefault(player1Id))
        Mockito.`when`(mAppState.mSelectedGamePlayerStats).thenReturn(BehaviorSubject.createDefault(playerStats))


        val testObserver= TestObserver<PlayerStats>()

        mShotChartViewModel.getPlayerStats()?.subscribeOn(mSchedulerProvider.computation())?.subscribe(testObserver)

        testObserver.assertNoErrors()

        testObserver.awaitDone(5,TimeUnit.SECONDS).assertValue(player1Stat)

    }

    @Test
    fun testGetPlayerStats_emitsNoValue_whenNotInGameSummary(){

        val player1Id = "000"
        val player1Stat =
            PlayerStats(
                Player(
                    "000",
                    "Test1 Player",
                    "00",
                    player1Id
                )
            )

        val player2Id = "001"
        val player2Stat =
            PlayerStats(
                Player(
                    "001",
                    "Test2 Player",
                    "01",
                    player2Id
                )
            )

        val playerStats = mapOf(player2Id to player2Stat)

        Mockito.`when`(mAppState.mSelectedPlayer).thenReturn(BehaviorSubject.createDefault(player1Id))
        Mockito.`when`(mAppState.mSelectedGamePlayerStats).thenReturn(BehaviorSubject.createDefault(playerStats))


        val testObserver= TestObserver<PlayerStats>()

        mShotChartViewModel.getPlayerStats()?.subscribeOn(mSchedulerProvider.computation())?.subscribe(testObserver)

        testObserver.assertNoErrors()

        testObserver.awaitDone(5,TimeUnit.SECONDS).assertNoValues()

    }
}