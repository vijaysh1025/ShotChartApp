package com.vijay.nbashottracker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.DatePicker
import com.vijay.nbashottracker.model.APIClient
import com.vijay.nbashottracker.model.DataStore
import com.vijay.nbashottracker.model.Game
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.annotations.Nullable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.create
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    @NonNull
    private var mCompositeDisposable: CompositeDisposable? = null

    @NonNull
    private var mViewModel:DailyScheduleViewModel?=null

    @NonNull
    private var mDatePicker:DatePicker?=null

    @Nullable
    private var mGameListView:RecyclerView?=null

    @Nullable
    private var mGameListAdapter:GameListAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewModel = (application as ShotTrackerApplication).getDailyScheduleViewModel()
        setupViews()
    }

    override fun onResume() {
        super.onResume()
        bind()
    }

    override fun onPause() {
        super.onPause()
        unbind()
    }

    private fun setupViews(){
        mGameListView = findViewById(R.id.gameList)
        mGameListAdapter = GameListAdapter()
        mDatePicker = findViewById(R.id.datePicker)
        mDatePicker?.setOnDateChangedListener {datePicker: DatePicker?, year: Int, month: Int, day: Int ->  mViewModel?.dateSelected(LocalDate.of(year,month+1,day))}

        mGameListView?.adapter = mGameListAdapter
        mGameListView?.layoutManager = LinearLayoutManager(this)
    }

    private fun bind(){
        mCompositeDisposable = CompositeDisposable()
        mCompositeDisposable?.add(mViewModel!!.getGames()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::setGames))



    }

    private fun unbind(){
        mCompositeDisposable?.clear()
    }

    private fun setGames(games:List<Game>){
        mGameListAdapter?.games = games
    }
}
