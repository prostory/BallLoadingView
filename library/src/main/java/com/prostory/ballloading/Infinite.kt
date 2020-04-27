package com.prostory.ballloading

import android.graphics.Path
import android.graphics.RectF

class Infinite : RunningTrack {
    private val rect by lazy { RectF() }
    private val path by lazy { Path() }
    
    override fun getPath(radX: Float, radY: Float, ballRadius: Float, ballSize: Int): Path {
        val rx = (radX - ballRadius)/2
        val ry = (radY - ballRadius)
        path.reset()
        rect.set(radX,
            radY - ry,
            radX + 2 * rx,
            radY + ry)
        path.addOval(rect, Path.Direction.CW)
        rect.set(radX - 2 * rx,
            radY - ry,
            radX,
            radY + ry)
        path.addOval(rect, Path.Direction.CCW)
        return path
    }

    override fun getFraction(factor: Float): Float =
        if (factor in 0.0f..0.25f) {
            factor
        } else if (factor < 0.75f) {
            factor + 0.25f
        } else {
            factor - 0.5f
        }
}