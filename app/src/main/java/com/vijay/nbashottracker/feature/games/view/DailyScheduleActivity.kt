package com.vijay.nbashottracker.feature.games.view

import android.content.Intent
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.DatePicker
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.vijay.nbashottracker.feature.games.viewmodels.DailyScheduleViewModel
import com.vijay.nbashottracker.R
import com.vijay.nbashottracker.core.platform.BaseActivity
import com.vijay.nbashottracker.feature.games.state.AppState
import com.vijay.nbashottracker.feature.games.state.objects.GameItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.annotations.Nullable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.time.LocalDate
import javax.inject.Inject

class DailyScheduleActivity : BaseActivity(), GameItemClickListener {

    @NonNull
    private var mCompositeDisposable: CompositeDisposable? = null

    @Inject lateinit var viewModel: DailyScheduleViewModel

    @JvmField
    @BindView(R.id.datePicker)
    var mDatePicker:DatePicker?=null

    @JvmField
    @BindView(R.id.gameListProgressLayout)
    var mLoadingBar: ConstraintLayout?=null

    @JvmField
    @BindView(R.id.gameList)
    var mGameListView: RecyclerView?=null

    @Nullable
    var mGameListAdapter: GameListAdapter?=null

    private lateinit var unbinder:Unbinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_schedule)
        appComponent.inject(this)
        unbinder = ButterKnife.bind(this)
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

    override fun onDestroy() {
        super.onDestroy()
        unbinder.unbind()
    }

    private fun setupViews(){
        mGameListAdapter = GameListAdapter(this)

        mDatePicker?.maxDate = viewModel.maxDate
        mDatePicker?.setOnDateChangedListener {datePicker: DatePicker?, year: Int, month: Int, day: Int ->  viewModel.dateSelected(LocalDate.of(year,month+1,day))}

        mGameListView?.adapter = mGameListAdapter
        mGameListView?.layoutManager = LinearLayoutManager(this)
    }

    private fun bind(){

        mCompositeDisposable = CompositeDisposable()

        mCompositeDisposable?.add(viewModel.getPlayerStats()
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.computation())
            .subscribe(
                {viewModel.setPlayerStats(it)},
                {onError(it)}
            ))

        mCompositeDisposable?.add(viewModel.getGames()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {setGames(it)},
                {onError(it)}
            ))

        mCompositeDisposable?.add(viewModel.getDateSubject()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {toggleLoadingBar(show = true)},
                {onError(it)}
            ))

        mCompositeDisposable?.add(viewModel.getCurrentGameSubject()
            .filter { g-> g!= com.vijay.nbashottracker.feature.games.state.AppState.EMPTY_GAME }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {toggleLoadingBar(true)},
                {onError(it)}
            ))

        mCompositeDisposable?.add(viewModel.getPlayerStatsSubject()
            .filter { s-> s!= com.vijay.nbashottracker.feature.games.state.AppState.EMPTY_STATS }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {showShotChart()},
                {onError(it)}
            ))
    }

    private fun unbind(){
        mCompositeDisposable?.clear()
    }

    private fun setGames(games:List<GameItem>){
        toggleLoadingBar(false)
        mGameListAdapter?.games = games
    }

    private fun toggleLoadingBar(show:Boolean){

        mLoadingBar?.animation?.cancel()
        mLoadingBar?.clearAnimation()
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
            }
        }
    }

    override fun onClickGame(game: GameItem){
        viewModel.gameSelected(game)
    }

    private fun showShotChart(){
        //toggleLoadingBar(false)
        val intent = Intent(this, ShotChartActivity::class.java)
        startActivity(intent)
    }

}
