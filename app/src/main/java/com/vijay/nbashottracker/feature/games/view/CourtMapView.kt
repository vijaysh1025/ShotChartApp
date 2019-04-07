package com.vijay.nbashottracker.feature.games.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.AnimatedVectorDrawable
import android.util.AttributeSet
import android.view.View
import com.vijay.nbashottracker.R

/**
 * TODO: document your custom view class.
 */
class CourtMapView : View {

    /**
     * In the example view, this drawable is drawn above the text.
     */
    var courtVectorDrawable: AnimatedVectorDrawable? = null

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
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.CourtMapView, defStyle, 0
        )

        if (a.hasValue(R.styleable.CourtMapView_exampleDrawable)) {
            courtVectorDrawable = a.getDrawable(
                R.styleable.CourtMapView_exampleDrawable
            ) as AnimatedVectorDrawable
            courtVectorDrawable?.callback = this
        }

        a.recycle()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = Math.min(measuredWidth, measuredHeight)
        setMeasuredDimension(size,size)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom

        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingTop - paddingBottom

        // Draw the example drawable on top of the text.
        courtVectorDrawable?.let {
            it.setBounds(
                paddingLeft, paddingTop,
                paddingLeft + contentWidth, paddingTop + contentHeight
            )
            it.draw(canvas)
        }
    }
}
