package com.prostory.ballloading

import android.graphics.Path
import android.graphics.PointF
import kotlin.math.cos
import kotlin.math.sin

class Diamond : RunningTrack {
    private val path by lazy { Path() }

    override fun getPath(radX: Float, radY: Float, ballRadius: Float, ballSize: Int): Path {
        val radius = radX.coerceAtMost(radY) - ballRadius
        path.reset()
        (0 until 4).forEach {
            if (it == 0) {
                path.moveTo(getPoint(radX, radY, radius, 0f))
            } else {
                path.lineTo(getPoint(radX, radY, radius, it * 90f))
            }
        }
        path.close()
        return path
    }

    private fun getPoint(centerX: Float, centerY: Float, radius: Float, angle: Float) : PointF {
        val degree = angle / 180 * Math.PI
        return PointF(centerX + radius * sin(degree).toFloat(),
            centerY - radius * cos(degree).toFloat())
    }
}