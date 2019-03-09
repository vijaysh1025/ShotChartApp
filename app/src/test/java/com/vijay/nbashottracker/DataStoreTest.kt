package com.vijay.nbashottracker

import android.util.Log
import com.vijay.nbashottracker.model.APIClient
import com.vijay.nbashottracker.model.DailySchedule
import com.vijay.nbashottracker.model.DataStore
import com.vijay.nbashottracker.model.Game
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subscribers.TestSubscriber
import junit.framework.Assert
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations
import retrofit2.create
import java.time.LocalDate
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class DataStoreTest{

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

    }

    @Test
    fun getScheduleOfDayTest(){
        var testSubscriber = TestSubscriber<DailySchedule>()
        val testObserver = TestObserver<DailySchedule>()
        val testScheduler = TestScheduler()
        val date = LocalDate.parse("2015-12-25")
        val datePath = "${date.year} ${date.monthValue} ${date.dayOfMonth}"
        System.out.println(datePath)
        var disposable = APIClient.instance
            ?.create<DataStore>()
            ?.getScheduleOfDay(date.year.toString(), date.monthValue.toString(), date.dayOfMonth.toString())
            ?.subscribeOn(testScheduler)
            ?.subscribe(testObserver)

        testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)



        testObserver.assertValue { t->(t.games as List<Game>).get(0).id == "b55c5579-950b-4726-8d36-6467f6caa772" }
    }

}