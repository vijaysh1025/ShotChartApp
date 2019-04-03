package com.vijay.nbashottracker.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.vijay.nbashottracker.R
import com.vijay.nbashottracker.ShotChartViewModel

import com.vijay.nbashottracker.ShotTrackerApplication
import com.vijay.nbashottracker.model.dailyschedule.Game
import com.vijay.nbashottracker.model.dailyschedule.Team
import com.vijay.nbashottracker.model.summary.PlayersItem

import com.vijay.nbashottracker.state.TeamType
import com.vijay.nbashottracker.state.objects.PlayerStats

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

import com.google.ar.sceneform.ux.ArFragment

import com.google.ar.core.TrackingState

import com.google.ar.core.HitResult

import android.view.View
import com.google.ar.core.Plane


import android.net.Uri
import com.google.ar.sceneform.ux.TransformableNode
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.core.Anchor
import com.google.ar.sceneform.rendering.ModelRenderable

import android.support.v7.app.AlertDialog
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ViewRenderable
import com.vijay.nbashottracker.ShotSpotView


class ShotChartARActivity : AppCompatActivity(),  NumberPicker.OnValueChangeListener{


    @NonNull
    private var mCompositeDisposable: CompositeDisposable? = null

    @NonNull
    private var mViewModel: ShotChartViewModel?=null

    @NonNull
    private var fragment: ArFragment? = null

    @NonNull
    private var placeButton: Button? = null

    private val pointer = PointerDrawable()
    private var isTracking: Boolean = false
    private var isHitting: Boolean = false
    private var courtMapView:CourtMapView?=null

    private var placed:Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shot_chart_ar)

        fragment = (supportFragmentManager.findFragmentById(R.id.sceneform_fragment) as ArFragment)
        fragment?.arSceneView?.scene?.addOnUpdateListener { frameTime ->
            fragment?.onUpdate(frameTime)
            onUpdate()
        }
        fragment?.planeDiscoveryController?.hide()

        mViewModel = (application as ShotTrackerApplication).getShotChartViewModel()
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

    private fun onUpdate() {
        val trackingChanged = updateTracking()
        val contentView = findViewById<View>(android.R.id.content)
        if (trackingChanged) {
            if (isTracking) {
                contentView.overlay.add(pointer)
            } else {
                contentView.overlay.remove(pointer)
            }
            contentView.invalidate()
        }

        if (isTracking) {
            val hitTestChanged = updateHitTest()
            if (hitTestChanged) {
                pointer.isEnabled = isHitting
                contentView.invalidate()
            }
        }

        Log.i("Tracking : ", isTracking.toString())
    }

    private fun updateTracking(): Boolean {
        val frame = fragment?.arSceneView?.arFrame
        val wasTracking = isTracking
        isTracking = frame != null && frame.camera.trackingState === TrackingState.TRACKING
        return isTracking !== wasTracking
    }

    private fun updateHitTest(): Boolean {
        val frame = fragment?.arSceneView?.arFrame
        val pt = getScreenCenter()
        val hits: List<HitResult>
        val wasHitting = isHitting
        isHitting = false
        if (frame != null) {
            hits = frame.hitTest(pt.x.toFloat(), pt.y.toFloat())
            for (hit in hits) {
                val trackable = hit.trackable
                if (trackable is Plane && (trackable as Plane).isPoseInPolygon(hit.hitPose)) {
                    isHitting = true
                    break
                }
            }
        }

        return wasHitting != isHitting
    }

    private fun getScreenCenter(): android.graphics.Point {
        val vw = findViewById<View>(android.R.id.content)
        return android.graphics.Point(vw.getWidth() / 2, vw.getHeight() / 2)
    }

    private fun setupViews(){
        placeButton = findViewById(R.id.place_object)
        placeButton?.setOnClickListener {
            if(placed){
                var court:CourtMapView = findViewById(R.id.court_map_view)
                court.courtVectorDrawable?.start()
            }else{
            addObject(null)
        Log.i("Place View", "clicked")}
        }

    }

    private fun bind(){

        //mCompositeDisposable = CompositeDisposable()
        /*mCompositeDisposable?.add(mViewModel!!.getCurrentGameSubject()
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
            .subscribe(this::loadTeamData))*/
    }

    private fun unbind(){
        //mCompositeDisposable?.clear()
    }

    private fun setTeamNames(game:Game){
        val homeLogoId = resources.getIdentifier((game.home as Team).alias.toLowerCase(),"drawable", this.applicationContext.packageName)
        val awayLogoId = resources.getIdentifier((game.away as Team).alias.toLowerCase(),"drawable", this.applicationContext.packageName)

    }

    private fun setPlayers(players:List<PlayersItem?>?){

    }

    override fun onBackPressed() {
        super.onBackPressed()
        mViewModel?.gameCleared()
        mViewModel?.statsCleared()
        var intent = Intent(this, DailyScheduleActivity::class.java)
        startActivity(intent)
    }

    override fun onValueChange(p0: NumberPicker?, p1: Int, p2: Int) {

    }

    override fun finish() {
        super.finish()
    }

    private fun loadShotChart(playerStats:PlayerStats){

    }

    private fun loadTeamData(teamType:TeamType){

    }

    private fun addObject(model: Uri?) {
        val frame = fragment?.arSceneView?.arFrame
        val pt = getScreenCenter()
        val hits: List<HitResult>
        if (frame != null) {
            hits = frame.hitTest(pt.x.toFloat(), pt.y.toFloat())
            for (hit in hits) {
                val trackable = hit.trackable
                if (trackable is Plane && trackable.isPoseInPolygon(hit.hitPose)) {
                    //placeObject(fragment!!, hit.createAnchor(), model)
                    Log.i("Place View", "called")
                    placeView(fragment!!,hit.createAnchor())
                    break

                }
            }
        }
    }

    private fun placeView(fragment: ArFragment, anchor: Anchor){
        val courtView = ViewRenderable.builder()
            .setView(fragment.context, R.layout.fragment_court_map)
            .build()
            .thenAccept { viewRenderable ->
                addNodeToScene(fragment,anchor,viewRenderable)
            }
        placed = true
    }

    private fun placeObject(fragment: ArFragment, anchor: Anchor, model: Uri) {
        val renderableFuture = ModelRenderable.builder()
            .setSource(fragment.context, model)
            .build()
            .thenAccept { renderable -> addNodeToScene(fragment, anchor, renderable) }
            .exceptionally { throwable ->
                val builder = AlertDialog.Builder(this)
                builder.setMessage(throwable.message)
                    .setTitle("Codelab error!")
                val dialog = builder.create()
                dialog.show()
                null
            }
    }

    private fun addNodeToScene(fragment: ArFragment, anchor: Anchor, renderable: Renderable) {
        val anchorNode = AnchorNode(anchor)
        val node = TransformableNode(fragment.transformationSystem)
        node.renderable = renderable
        node.setParent(anchorNode)
        fragment.arSceneView.scene.addChild(anchorNode)
        node.select()
    }
}
