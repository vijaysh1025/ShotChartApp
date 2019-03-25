package com.vijay.nbashottracker.ui

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.*
import com.vijay.nbashottracker.R
import com.vijay.nbashottracker.ShotChartViewModel
import com.vijay.nbashottracker.ShotSpotView
import com.vijay.nbashottracker.ShotTrackerApplication
import com.vijay.nbashottracker.model.dailyschedule.Game
import com.vijay.nbashottracker.model.dailyschedule.Team
import com.vijay.nbashottracker.model.summary.PlayersItem
import com.vijay.nbashottracker.state.AppState
import com.vijay.nbashottracker.state.ShotState
import com.vijay.nbashottracker.state.TeamType
import com.vijay.nbashottracker.state.objects.PlayerStats
import com.wefika.horizontalpicker.HorizontalPicker
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_shot_chart.*
import timber.log.Timber
import java.util.*

class ShotChartActivity : AppCompatActivity(),  NumberPicker.OnValueChangeListener{


    @NonNull
    private var mCompositeDisposable: CompositeDisposable? = null

    @NonNull
    private var mViewModel: ShotChartViewModel?=null

    @NonNull
    private var mHomeTeamToggle: CardView?=null

    @NonNull
    private var mAwayTeamToggle: CardView?=null

    @NonNull
    private var mHomeTeamLogo:ImageView?=null

    @NonNull
    private var mAwayTeamLogo:ImageView?=null


    @NonNull
    private var mPlayerPicker:NumberPicker?=null

    @NonNull
    private var mPlayerName:TextView?=null

    @NonNull
    private var mPlayerNumber:TextView?=null

    @NonNull
    private var mCourtMapView:CourtMapView?=null

    @NonNull
    private var mShotSpots:MutableList<ShotSpotView> = mutableListOf()

    @NonNull
    private var mPlayerIds:MutableList<String?> = mutableListOf()


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
        mHomeTeamLogo = findViewById(R.id.home_team_button_logo)
        mAwayTeamLogo = findViewById(R.id.away_team_button_logo)
        mHomeTeamToggle = findViewById(R.id.home_team_toggle)
        mAwayTeamToggle = findViewById(R.id.away_team_toggle)
        mPlayerPicker = findViewById(R.id.player_picker)
        mPlayerName = findViewById(R.id.player_name)
        mPlayerNumber = findViewById(R.id.player_number)
        mCourtMapView = findViewById(R.id.court_map_view)

        mPlayerPicker?.setOnValueChangedListener(this)

        mHomeTeamToggle?.setOnClickListener {
            mViewModel?.teamSelected(TeamType.HOME)
        }

        mAwayTeamToggle?.setOnClickListener {
            mViewModel?.teamSelected(TeamType.AWAY)
        }


        val group:ViewGroup = findViewById(R.id.shot_chart_main)

        val rand = Random()
        mShotSpots.clear()
        for(x in 0..100){
            val iShotSpot = ShotSpotView(this)

            iShotSpot.x = 800.0f*rand.nextFloat()
            iShotSpot.y = 800.0f*rand.nextFloat()
            iShotSpot.elevation = 50f
            iShotSpot.alpha = 0f
            group.addView(iShotSpot,40,40)

            mShotSpots.add(x, iShotSpot)

        }
    }

    private fun bind(){

        mCompositeDisposable = CompositeDisposable()
        mCompositeDisposable?.add(mViewModel!!.getCurrentGameSubject()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::setTeamNames))

        mCompositeDisposable?.add(mViewModel!!.getTeamPlayers()!!
            .doOnError { t:Throwable -> Log.d("ShotChartActivity", t.cause.toString()) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({setPlayers(it)},{ setPlayers(null)}))

        mCompositeDisposable?.add(mViewModel!!.getPlayerStats()!!
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::loadShotChart))

        mCompositeDisposable?.add(mViewModel!!.getTeamSelected()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::loadTeamData))
    }

    private fun unbind(){
        mCompositeDisposable?.clear()
    }

    private fun setTeamNames(game:Game){
        val homeLogoId = resources.getIdentifier((game.home as Team).alias.toLowerCase(),"drawable", this.applicationContext.packageName)
        val awayLogoId = resources.getIdentifier((game.away as Team).alias.toLowerCase(),"drawable", this.applicationContext.packageName)

        mHomeTeamLogo?.setImageResource(homeLogoId)
        mAwayTeamLogo?.setImageResource(awayLogoId)
    }

    private fun setPlayers(players:List<PlayersItem?>?){
        if(players==null)
            mPlayerPicker?.displayedValues = arrayOf("30","32","35","45","43")
        else {
            val playerIds= players?.map { it?.jerseyNumber.toString() }.toTypedArray()

            mPlayerPicker?.minValue = 0
            mPlayerPicker?.maxValue = playerIds.count()-1
            mPlayerPicker?.displayedValues = playerIds
            mPlayerIds.clear()
            mPlayerIds = players.map{it?.id}.toMutableList()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mViewModel?.gameCleared()
        mViewModel?.statsCleared()
        var intent = Intent(this, DailyScheduleActivity::class.java)
        startActivity(intent)
    }

    override fun onValueChange(p0: NumberPicker?, p1: Int, p2: Int) {
        mViewModel?.playerSelected(mPlayerIds[p0!!.value]!!)
    }

    override fun finish() {
        super.finish()
    }

    private fun loadShotChart(playerStats:PlayerStats){
        val group:ViewGroup = findViewById(R.id.shot_chart_main)
        val width = group.measuredWidth-group.paddingLeft-group.paddingRight

        mPlayerName?.text = playerStats.playerName
        mPlayerNumber?.text = playerStats.playerNumber
        for(i in 0..100){
            if(i<playerStats.fieldGoalEvents.count()){
                var posX = playerStats.fieldGoalEvents[i].positionX!!*width
                var posY = playerStats.fieldGoalEvents[i].positionY!!*width

                mShotSpots[i].alpha =1f
                mShotSpots[i].animate().x(posY).start()
                mShotSpots[i].animate().y(posX).start()
                mShotSpots[i].morph(playerStats.fieldGoalEvents[i].isMade)
            }else{
                mShotSpots[i].alpha =0f
            }
        }
    }

    private fun loadTeamData(teamType:TeamType){
        val homeScale = if(teamType==TeamType.HOME) 1.05f else 0.95f
        val awayScale = if(teamType==TeamType.AWAY) 1.05f else 0.95f


        mHomeTeamToggle?.animate()?.apply {
            interpolator=AccelerateInterpolator()
            duration=500
            scaleX(homeScale)
            scaleY(homeScale)
        }

        mAwayTeamToggle?.animate()?.apply {
            interpolator=AccelerateInterpolator()
            duration=500
            scaleX(awayScale)
            scaleY(awayScale)
        }

    }
}
