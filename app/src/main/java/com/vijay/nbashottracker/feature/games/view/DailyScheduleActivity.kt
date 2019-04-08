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

/**
 * View for displaying a list of games for a given date.
 */
class DailyScheduleActivity : BaseActivity(), GameItemClickListener {

    @NonNull
    private var mCompositeDisposable: CompositeDisposable? = null

    /**
     * View model for updating UI and communicating state change information
     */
    @Inject lateinit var viewModel: DailyScheduleViewModel

    /**
     * Primary View items in activity
     */
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

    /**
     * Dagger initialization, Butterknife binding, and view setup
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_schedule)
        appComponent.inject(this)
        unbinder = ButterKnife.bind(this)
        setupViews()
    }

    /**
     * RxJava observable and state Binding call
     */
    override fun onResume() {
        super.onResume()
        bind()
    }

    /**
     * RxJava observable and state unBinding call
     */
    override fun onPause() {
        super.onPause()
        unbind()
    }

    /**
     * Butterknife view unbinding
     */
    override fun onDestroy() {
        super.onDestroy()
        unbinder.unbind()
    }

    /**
     * View specific initializations
     * Datepicker -> Max date should be one less than today to only display games that have shot charts
     * Game List Adapter initialization
     */
    private fun setupViews(){
        mGameListAdapter = GameListAdapter(this)

        mDatePicker?.maxDate = viewModel.maxDate
        mDatePicker?.setOnDateChangedListener {datePicker: DatePicker?, year: Int, month: Int, day: Int ->  viewModel.dateSelected(LocalDate.of(year,month+1,day))}

        mGameListView?.adapter = mGameListAdapter
        mGameListView?.layoutManager = LinearLayoutManager(this)
    }

    /**
     * RxJava bindings
     */
    private fun bind(){

        mCompositeDisposable = CompositeDisposable()

        // Set player stats state when game is clicked
        mCompositeDisposable?.add(viewModel.getPlayerStats()
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.computation())
            .subscribe(
                {viewModel.setPlayerStats(it)},
                {onError(it)}
            ))

        // Set games for a given date
        mCompositeDisposable?.add(viewModel.getGames()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {setGames(it)},
                {onError(it)}
            ))

        // Start loading bar when date changes.
        mCompositeDisposable?.add(viewModel.getDateSubject()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {toggleLoadingBar(show = true)},
                {onError(it)}
            ))

        // Start loading bar when game is selected
        mCompositeDisposable?.add(viewModel.getCurrentGameSubject()
            .filter { g-> g!= com.vijay.nbashottracker.feature.games.state.AppState.EMPTY_GAME }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {toggleLoadingBar(true)},
                {onError(it)}
            ))

        // when player stats are emitted, show shot chart activity
        mCompositeDisposable?.add(viewModel.getPlayerStatsSubject()
            .filter { s-> s!= com.vijay.nbashottracker.feature.games.state.AppState.EMPTY_STATS }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {showShotChart()},
                {onError(it)}
            ))
    }

    // Unbind all Rx Objects
    private fun unbind(){
        mCompositeDisposable?.clear()
    }

    // When games returned from network request, load games to Recycler view and hide loading bar
    private fun setGames(games:List<GameItem>){
        toggleLoadingBar(false)
        mGameListAdapter?.games = games
    }

    // Handle loading bar show/hide states
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

    // when game is clicked update appropriate state in ViewModel->AppState
    override fun onClickGame(game: GameItem){
        viewModel.gameSelected(game)
    }

    // Load shot chart activity
    private fun showShotChart(){
        //toggleLoadingBar(false)
        val intent = Intent(this, ShotChartActivity::class.java)
        startActivity(intent)
    }

}
