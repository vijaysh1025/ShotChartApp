package com.vijay.nbashottracker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.DatePicker
import com.vijay.nbashottracker.model.dailyschedule.Game
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.annotations.Nullable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.time.LocalDate

class DailyScheduleActivity : AppCompatActivity() {

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

    @Nullable
    private  var mLoadingBar:ConstraintLayout?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_schedule)

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
        mLoadingBar = findViewById(R.id.gameListProgressLayout)

        mGameListView?.adapter = mGameListAdapter
        mGameListView?.layoutManager = LinearLayoutManager(this)
    }

    private fun bind(){
        mCompositeDisposable = CompositeDisposable()
        mCompositeDisposable?.add(mViewModel!!.getGames()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::setGames))
        mCompositeDisposable?.add(mViewModel!!.getDate()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{toggleLoadingBar(show = true)})
    }

    private fun unbind(){
        mCompositeDisposable?.clear()
    }

    private fun setGames(games:List<Game>){
        toggleLoadingBar(false)
        mGameListAdapter?.games = games
    }

    private fun toggleLoadingBar(show:Boolean){
        if(show) {
            mLoadingBar?.visibility = View.VISIBLE
            mLoadingBar?.animate()?.apply {
                interpolator = AccelerateInterpolator()
                duration = 200
                alpha(1f)
                start()
            }
        }
        else {
            mLoadingBar?.animate()?.apply {
                interpolator = AccelerateInterpolator()
                duration = 200
                alpha(0f)
                start()
            }?.withEndAction{
                mLoadingBar?.visibility = View.INVISIBLE
            }

        }
    }
}
