package com.vijay.nbashottracker

import com.vijay.nbashottracker.datamodel.IDataModel
import com.vijay.nbashottracker.model.dailyschedule.*
import com.vijay.nbashottracker.model.playbyplay.Player
import com.vijay.nbashottracker.model.summary.PlayersItem
import com.vijay.nbashottracker.core.schedulers.TestSchedulerProvider
import com.vijay.nbashottracker.state.IAppState
import com.vijay.nbashottracker.state.TeamType
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
import java.util.concurrent.TimeUnit

class ShotChartViewModelTest{
    @Mock
    private var mDataModel:IDataModel?=null

    private var mShotChartViewModel:ShotChartViewModel?=null

    private var mSchedulerProvider:TestSchedulerProvider? = null

    @Mock
    private var mAppState:IAppState?=null

    @Throws(Exception::class)
    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        mSchedulerProvider = TestSchedulerProvider()
        mShotChartViewModel = ShotChartViewModel(mDataModel!!, mSchedulerProvider!!,mAppState!!)
    }

    @Test
    fun testGetTeamPlayers_emitsPlayerList_whenTeamSelected(){
        val game = Game("000",null,null,null)
        val player1 = PlayersItem("111","Test Player",null,null,null,"00")
        val players = listOf(player1)

        Mockito.`when`(mDataModel?.getTeamPlayers(game.id,true)).thenReturn(Single.just(players))
        Mockito.`when`(mAppState?.mSelectedTeam).thenReturn(BehaviorSubject.createDefault(TeamType.HOME))
        Mockito.`when`(mAppState?.mSelectedGame).thenReturn(BehaviorSubject.createDefault(game))


        var testObserver= TestObserver<List<PlayersItem?>>()

        mShotChartViewModel!!.getTeamPlayers()?.subscribeOn(mSchedulerProvider?.computation())?.subscribe(testObserver)

        testObserver.assertNoErrors()

        testObserver.awaitDone(5,TimeUnit.SECONDS).assertValue(players)

    }

    @Test
    fun testGetPlayerStats_emitsPlayerStats_whenPlayerSelected(){

        val player1Id = "000"
        val player1Stat = PlayerStats(Player("000","Test1 Player","00", player1Id))

        val player2Id = "001"
        val player2Stat = PlayerStats(Player("001","Test2 Player","01", player2Id))

        val playerStats = mapOf(player1Id to player1Stat, player2Id to player2Stat)

        Mockito.`when`(mAppState?.mSelectedPlayer).thenReturn(BehaviorSubject.createDefault(player1Id))
        Mockito.`when`(mAppState?.mSelectedGamePlayerStats).thenReturn(BehaviorSubject.createDefault(playerStats))


        var testObserver= TestObserver<PlayerStats>()

        mShotChartViewModel!!.getPlayerStats()?.subscribeOn(mSchedulerProvider?.computation())?.subscribe(testObserver)

        testObserver.assertNoErrors()

        testObserver.awaitDone(5,TimeUnit.SECONDS).assertValue(player1Stat)

    }

    @Test
    fun testGetPlayerStats_emitsNoValue_whenNotInGameSummary(){

        val player1Id = "000"
        val player1Stat = PlayerStats(Player("000","Test1 Player","00", player1Id))

        val player2Id = "001"
        val player2Stat = PlayerStats(Player("001","Test2 Player","01", player2Id))

        val playerStats = mapOf(player2Id to player2Stat)

        Mockito.`when`(mAppState?.mSelectedPlayer).thenReturn(BehaviorSubject.createDefault(player1Id))
        Mockito.`when`(mAppState?.mSelectedGamePlayerStats).thenReturn(BehaviorSubject.createDefault(playerStats))


        var testObserver= TestObserver<PlayerStats>()

        mShotChartViewModel!!.getPlayerStats()?.subscribeOn(mSchedulerProvider?.computation())?.subscribe(testObserver)

        testObserver.assertNoErrors()

        testObserver.awaitDone(5,TimeUnit.SECONDS).assertNoValues()

    }
}