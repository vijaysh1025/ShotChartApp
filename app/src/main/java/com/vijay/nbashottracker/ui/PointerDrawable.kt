package com.vijay.nbashottracker.ui

import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.Log

class PointerDrawable : Drawable() {
    private val paint = Paint()
    var isEnabled: Boolean = false
    override fun draw(canvas: Canvas) {
        val cx = (canvas.width / 2).toFloat()
        val cy = (canvas.height / 2).toFloat()
        if (isEnabled) {
            paint.color = Color.GREEN
            canvas.drawCircle(cx, cy, 50f, paint)
        } else {
            paint.color = Color.GRAY
            canvas.drawCircle(cx, cy, 50f, paint)
            //canvas.drawText("X", cx, cy, paint)
        }
    }

    override fun setAlpha(i: Int) {

    }

    override fun setColorFilter(colorFilter: ColorFilter?) {

    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }
}