package com.vijay.nbashottracker.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.DatePicker
import com.vijay.nbashottracker.DailyScheduleViewModel
import com.vijay.nbashottracker.R
import com.vijay.nbashottracker.ShotTrackerApplication
import com.vijay.nbashottracker.model.dailyschedule.Game
import com.vijay.nbashottracker.state.AppState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.annotations.Nullable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.time.LocalDate

class DailyScheduleActivity : AppCompatActivity(), GameItemClickListener {

    @NonNull
    private var mCompositeDisposable: CompositeDisposable? = null

    @NonNull
    private var mViewModel: DailyScheduleViewModel?=null

    @NonNull
    private var mDatePicker:DatePicker?=null

    @Nullable
    private var mGameListView:RecyclerView?=null

    @Nullable
    private var mGameListAdapter: GameListAdapter?=null

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
        mGameListAdapter = GameListAdapter(this)
        mDatePicker = findViewById(R.id.datePicker)
        mDatePicker?.setOnDateChangedListener {datePicker: DatePicker?, year: Int, month: Int, day: Int ->  mViewModel?.dateSelected(LocalDate.of(year,month+1,day))}
        mLoadingBar = findViewById(R.id.gameListProgressLayout)

        mGameListView?.adapter = mGameListAdapter
        mGameListView?.layoutManager = LinearLayoutManager(this)
    }

    private fun bind(){
        Log.d("DailySchedule", "Bind")
        mCompositeDisposable = CompositeDisposable()

        mCompositeDisposable?.add(mViewModel!!.getGames()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::setGames))

        mCompositeDisposable?.add(mViewModel!!.getDateSubject()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{toggleLoadingBar(show = true)})

        mCompositeDisposable?.add(mViewModel!!.getCurrentGameSubject()
            .filter { g:Game-> g!=AppState.EMPTY_GAME }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::showShotChart))
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

    override fun onClickGame(game:Game){
        mViewModel?.gameSelected(game)
    }

    private fun showShotChart(game:Game?){
        var intent = Intent(this, ShotChartActivity::class.java)
        startActivity(intent)
    }
}
