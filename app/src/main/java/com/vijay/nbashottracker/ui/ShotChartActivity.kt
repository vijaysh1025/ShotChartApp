package com.vijay.nbashottracker.ui

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.ToggleButton
import com.vijay.nbashottracker.R
import com.vijay.nbashottracker.ShotChartViewModel
import com.vijay.nbashottracker.ShotSpotView
import com.vijay.nbashottracker.ShotTrackerApplication
import com.vijay.nbashottracker.model.dailyschedule.Game
import com.vijay.nbashottracker.model.summary.PlayersItem
import com.vijay.nbashottracker.state.AppState
import com.wefika.horizontalpicker.HorizontalPicker
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*

class ShotChartActivity : AppCompatActivity(),
    CourtMapFragment.OnFragmentInteractionListener {

    @NonNull
    private var mCompositeDisposable: CompositeDisposable? = null

    @NonNull
    private var mViewModel: ShotChartViewModel?=null

    @NonNull
    private var mHomeTeamToggle: ToggleButton?=null

    @NonNull
    private var mAwayTeamToggle: ToggleButton?=null

    @NonNull
    private var mTeamToggleGroup:RadioGroup?=null

    @NonNull
    private var mPlayerPicker:HorizontalPicker?=null

    @NonNull
    private var mCourtMapView:CourtMapView?=null

    @NonNull
    private var mShotSpots:MutableList<ShotSpotView> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shot_chart)

        mViewModel = (application as ShotTrackerApplication).getShotChartViewModel()
        setupViews()
    }

    override fun onResume() {
        super.onResume()
        mCourtMapView?.courtVectorDrawable?.start()
        bind()
    }

    override fun onPause() {
        super.onPause()
        unbind()
    }

    private fun setupViews(){
        mHomeTeamToggle = findViewById(R.id.home_team_toggle)
        mAwayTeamToggle = findViewById(R.id.away_team_toggle)
        mTeamToggleGroup = findViewById(R.id.team_toggle_group)
        mPlayerPicker = findViewById(R.id.player_picker)
        mCourtMapView = findViewById(R.id.court_map_view)

        mHomeTeamToggle?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Log.i("Click Register", "Clicked")
                val rand = Random()
                for(a in mShotSpots){
                    a.animate().x(800.0f*rand.nextFloat()).start()
                    a.animate().y(800.0f*rand.nextFloat()).start()
                    a.morph(rand.nextBoolean())
                }
            }
        })

        mTeamToggleGroup?.setOnCheckedChangeListener{radioGroup, i ->
            for(j in 1..radioGroup.childCount){
                var button:ToggleButton = radioGroup.getChildAt(j) as ToggleButton
                button.isChecked = i==j


            }
        }

        val group:ViewGroup = findViewById(R.id.shot_chart_main)

        val rand = Random()
        mShotSpots.clear()
        for(x in 0..9){
            val iShotSpot = ShotSpotView(this)

            iShotSpot.x = 800.0f*rand.nextFloat()
            iShotSpot.y = 800.0f*rand.nextFloat()
            group.addView(iShotSpot,30,30)

            mShotSpots.add(x, iShotSpot)

        }
    }

    private fun bind(){
        Log.d("ShotChartActivity", "Bind")
        mCompositeDisposable = CompositeDisposable()
        mCompositeDisposable?.add(mViewModel!!.getCurrentGameSubject()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::setTeamNames))

        //var players: Observable<List<PlayersItem?>>? = mViewModel!!.getTeamPlayers()
        mCompositeDisposable?.add(mViewModel!!.getTeamPlayers()!!
            .doOnError { t:Throwable -> Log.d("ShotChartActivity", t.cause.toString()) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({setPlayers(it)},{ setPlayers(null)}))
    }

    private fun unbind(){
        mCompositeDisposable?.clear()
    }

    private fun setTeamNames(game:Game){
        mHomeTeamToggle?.text = game.home?.alias
        mHomeTeamToggle?.textOn = game.home?.alias
        mHomeTeamToggle?.textOff = game.home?.alias

        mAwayTeamToggle?.text = game.away?.alias
        mAwayTeamToggle?.textOn = game.away?.alias
        mAwayTeamToggle?.textOff = game.away?.alias
    }

    private fun setPlayers(players:List<PlayersItem?>?){
        if(players==null)
            mPlayerPicker?.values = arrayOf("-","-","-")
        else
            mPlayerPicker?.values = players?.map { it?.jerseyNumber.toString() }.toTypedArray()
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mViewModel?.gameSelected(AppState.EMPTY_GAME)
    }
}
