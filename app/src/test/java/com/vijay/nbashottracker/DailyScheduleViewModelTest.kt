package com.vijay.nbashottracker

import com.vijay.nbashottracker.datamodel.IDataModel
import com.vijay.nbashottracker.model.dailyschedule.*
import com.vijay.nbashottracker.schedulers.TestSchedulerProvider
import io.reactivex.Single
import io.reactivex.observers.TestObserver
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

    @Throws(Exception::class)
    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        mSchedulerProvider = TestSchedulerProvider()
        mDailyScheduleViewModel = DailyScheduleViewModel(mDataModel!!, mSchedulerProvider!!)
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

        var testObserver= TestObserver<List<Game>>()

        mDailyScheduleViewModel!!.getGames().subscribeOn(mSchedulerProvider?.computation()).subscribe(testObserver)
        mDailyScheduleViewModel!!.dateSelected(date)

        testObserver.assertNoErrors()

        testObserver.awaitDone(5,TimeUnit.SECONDS).assertValue(games)
    }
}