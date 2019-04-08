package com.vijay.nbashottracker.feature.games.view

import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.widget.ImageView
import com.vijay.nbashottracker.R

/**
 * Shot spot representation of X and O.
 * ShapeShifter was used to morph animated X to O and O to X.
 */
class ShotSpotView : ImageView {

    var mCircleToXAVD: AnimatedVectorDrawable? = null
    var mXToCircleAVD: AnimatedVectorDrawable? = null

    var mIsShowingCircle:Boolean = true

    init {
        minimumWidth = 30
        maxWidth = 30
        minimumHeight = 30
        maxHeight = 30

    }

    constructor(context: Context) : super(context) {
        init()
    }


    private fun init() {
        mIsShowingCircle = true
        mCircleToXAVD = context.getDrawable(R.drawable.shot_spot_circle_to_x) as AnimatedVectorDrawable
        mXToCircleAVD = context.getDrawable(R.drawable.shot_spot_x_to_circle) as AnimatedVectorDrawable
        setImageDrawable(mCircleToXAVD)
    }

    public fun morph(toCircle:Boolean){
        if(toCircle == mIsShowingCircle)
            return
        mIsShowingCircle = toCircle
        val drawable = if(mIsShowingCircle) mXToCircleAVD else mCircleToXAVD

        setImageDrawable(drawable)
        drawable?.start()
    }
}
