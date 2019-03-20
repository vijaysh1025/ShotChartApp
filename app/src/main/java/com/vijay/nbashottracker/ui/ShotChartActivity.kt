package com.vijay.nbashottracker.ui

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import com.vijay.nbashottracker.state.ShotState
import com.wefika.horizontalpicker.HorizontalPicker
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_shot_chart.*
import timber.log.Timber
import java.util.*

class ShotChartActivity : AppCompatActivity(),  HorizontalPicker.OnItemSelected{

    @NonNull
    private var mCompositeDisposable: CompositeDisposable? = null

    @NonNull
    private var mViewModel: ShotChartViewModel?=null

    @NonNull
    private var mHomeTeamToggle: Button?=null

    @NonNull
    private var mAwayTeamToggle: Button?=null

    @NonNull
    private var mTeamToggleGroup:RadioGroup?=null

    @NonNull
    private var mPlayerPicker:HorizontalPicker?=null

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
        mHomeTeamToggle = findViewById(R.id.home_team_toggle)
        mAwayTeamToggle = findViewById(R.id.away_team_toggle)
        mPlayerPicker = findViewById(R.id.player_picker)
        mCourtMapView = findViewById(R.id.court_map_view)

        mPlayerPicker?.setOnItemSelectedListener(this)

//        mHomeTeamToggle?.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                val rand = Random()
//                for(a in mShotSpots){
//                    a.animate().x(800.0f*rand.nextFloat()).start()
//                    a.animate().y(800.0f*rand.nextFloat()).start()
//                    a.morph(rand.nextBoolean())
//                }
//            }
//        })


        val group:ViewGroup = findViewById(R.id.shot_chart_main)

        val rand = Random()
        mShotSpots.clear()
        for(x in 0..100){
            val iShotSpot = ShotSpotView(this)

            iShotSpot.x = 800.0f*rand.nextFloat()
            iShotSpot.y = 800.0f*rand.nextFloat()
            iShotSpot.alpha = 0f;
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

//        mCompositeDisposable?.add(mViewModel!!.getShotMap()
//            .subscribeOn(Schedulers.computation())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(this::loadShotChart))

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
        mAwayTeamToggle?.text = game.away?.alias
    }

    private fun setPlayers(players:List<PlayersItem?>?){
        if(players==null)
            mPlayerPicker?.values = arrayOf("-","-","-")
        else {
            mPlayerPicker?.values = players?.map { it?.jerseyNumber.toString() }.toTypedArray()
            mPlayerIds.clear()
            mPlayerIds = players.map{it?.id}.toMutableList()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mViewModel?.gameSelected(AppState.EMPTY_GAME)
    }

    override fun onItemSelected(index: Int) {
        mViewModel?.playerSelected(mPlayerIds[index]!!)
    }

    override fun finish() {
        super.finish()

    }

    private fun loadShotChart(shots:List<ShotState>){
        val group:ViewGroup = findViewById(R.id.shot_chart_main)
        for(i in 0..100){
            if(i<shots.count()){
                var posX = 0f
                var posY = 0f
                if(shots[i].pos.y.toFloat()<564){
                    posX = shots[i].pos.x.toFloat()/600*group.width
                    posY = shots[i].pos.y.toFloat()/600*group.width
                }else{
                    posX = (600-shots[i].pos.x.toFloat())/600*group.width
                    posY = (1128-shots[i].pos.y.toFloat())/600*group.width
                }


                mShotSpots[i].alpha =1f;
                mShotSpots[i].animate().x(posX).start()
                mShotSpots[i].animate().y(posY).start()
                mShotSpots[i].morph(shots[i].make)
            }else{
                mShotSpots[i].alpha =0f;
            }
        }
    }
}
