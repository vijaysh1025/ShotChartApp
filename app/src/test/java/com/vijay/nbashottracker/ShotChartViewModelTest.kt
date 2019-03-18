package com.vijay.nbashottracker

import com.vijay.nbashottracker.datamodel.IDataModel
import com.vijay.nbashottracker.model.dailyschedule.*
import com.vijay.nbashottracker.schedulers.TestSchedulerProvider
import com.vijay.nbashottracker.state.IAppState
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

//    @Test
//    fun testGetSelectedTeamId_emitsCorrectId_whenTeamTypeSelected{
//
//    }
}