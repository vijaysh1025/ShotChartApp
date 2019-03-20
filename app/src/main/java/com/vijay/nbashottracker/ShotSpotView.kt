package com.vijay.nbashottracker

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.widget.ImageView

/**
 * TODO: document your custom view class.
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
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
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
