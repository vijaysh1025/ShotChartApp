package com.vijay.nbashottracker.feature.games.view

import android.content.Intent
import android.os.Bundle
import androidx.cardview.widget.CardView
import android.util.Log
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.vijay.nbashottracker.R
import com.vijay.nbashottracker.feature.games.viewmodels.ShotChartViewModel
import com.vijay.nbashottracker.core.platform.BaseActivity
import com.vijay.nbashottracker.feature.games.state.TeamType
import com.vijay.nbashottracker.feature.games.state.objects.GameItem
import com.vijay.nbashottracker.feature.games.state.objects.PlayerItem
import com.vijay.nbashottracker.feature.games.state.objects.PlayerStats
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class ShotChartActivity : BaseActivity(),  NumberPicker.OnValueChangeListener{

    @NonNull
    private var mCompositeDisposable: CompositeDisposable? = null

    @Inject
    lateinit var mViewModel: ShotChartViewModel

    @JvmField
    @BindView(R.id.home_team_toggle)
    var mHomeTeamToggle: CardView?=null

    @JvmField
    @BindView(R.id.away_team_toggle)
    var mAwayTeamToggle: CardView?=null

    @JvmField
    @BindView(R.id.home_team_button_logo)
    var mHomeTeamLogo:ImageView?=null

    @JvmField
    @BindView(R.id.away_team_button_logo)
    var mAwayTeamLogo:ImageView?=null

    @JvmField
    @BindView(R.id.player_picker)
    var mPlayerPicker:NumberPicker?=null

    @JvmField
    @BindView(R.id.player_name)
    var mPlayerName:TextView?=null

    @JvmField
    @BindView(R.id.player_number)
    var mPlayerNumber:TextView?=null

    @JvmField
    @BindView(R.id.court_map_view)
    var mCourtMapView:CourtMapView?=null

    @NonNull
    private var mShotSpots:MutableList<ShotSpotView> = mutableListOf()

    @NonNull
    private var mPlayerIds:MutableList<String?> = mutableListOf()

    private lateinit var unbinder: Unbinder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shot_chart)
        appComponent.inject(this)
        unbinder = ButterKnife.bind(this)
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

    override fun onDestroy() {
        super.onDestroy()
        unbinder.unbind()
    }

    private fun setupViews(){
        mPlayerPicker?.setOnValueChangedListener(this)

        mHomeTeamToggle?.setOnClickListener {
            mViewModel.teamSelected(TeamType.HOME)
        }

        mAwayTeamToggle?.setOnClickListener {
            mViewModel.teamSelected(TeamType.AWAY)
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
        mCompositeDisposable?.add(mViewModel.getCurrentGameSubject()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::setTeamNames))

        mCompositeDisposable?.add(mViewModel.getTeamPlayers()!!
            .doOnError { t:Throwable -> Log.d("ShotChartActivity", t.cause.toString()) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({setPlayers(it)},{ setPlayers(null)}))

        mCompositeDisposable?.add(mViewModel.getPlayerStats()!!
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::loadShotChart))

        mCompositeDisposable?.add(mViewModel.getTeamSelected()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::loadTeamData))
    }

    private fun unbind(){
        mCompositeDisposable?.clear()
    }

    private fun setTeamNames(game: GameItem){
        val homeLogoId = resources.getIdentifier(game.homeTeam.alias.toLowerCase(),"drawable", this.applicationContext.packageName)
        val awayLogoId = resources.getIdentifier(game.awayTeam.alias.toLowerCase(),"drawable", this.applicationContext.packageName)

        mHomeTeamLogo?.setImageResource(homeLogoId)
        mAwayTeamLogo?.setImageResource(awayLogoId)
    }

    private fun setPlayers(players:List<PlayerItem?>?){
        if(players!=null) {
            val playerIds= players.map { it?.number.toString() }.toTypedArray()

            mPlayerPicker?.minValue = 0
            mPlayerPicker?.maxValue = playerIds.count()-1
            mPlayerPicker?.displayedValues = playerIds
            mPlayerIds.clear()
            mPlayerIds = players.map{it?.id}.toMutableList()

            mPlayerPicker?.value = 0
            mViewModel.playerSelected(mPlayerIds[0]!!)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        mViewModel.gameCleared()
        mViewModel.statsCleared()
        var intent = Intent(this, DailyScheduleActivity::class.java)
        startActivity(intent)
    }

    override fun onValueChange(p0: NumberPicker?, p1: Int, p2: Int) {
        mViewModel.playerSelected(mPlayerIds[p0!!.value]!!)
    }

    override fun finish() {
        super.finish()
    }

    private fun loadShotChart(playerStats: PlayerStats){
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

    private fun loadTeamData(teamType: TeamType){
        val homeScale = if(teamType== TeamType.HOME) 1.05f else 0.95f
        val awayScale = if(teamType== TeamType.AWAY) 1.05f else 0.95f


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
