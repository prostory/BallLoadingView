package com.prostory.ballloading

import android.graphics.Path
import android.graphics.PointF
import kotlin.math.cos
import kotlin.math.sin

class Star : RunningTrack {
    private val path by lazy { Path() }

    override fun getPath(radX: Float, radY: Float, ballRadius: Float, ballSize: Int): Path {
        val largeRadius = radX.coerceAtMost(radY) - ballRadius
        val smallRadius = largeRadius / 3
        path.reset()
        (0 until 12).forEach {
            if (it == 0) {
                path.moveTo(getPoint(radX, radY, largeRadius, it.toFloat()))
            } else {
                if (it % 2 == 0) {
                    path.lineTo(getPoint(radX, radY, largeRadius, it * 30f))
                } else {
                    path.lineTo(getPoint(radX, radY, smallRadius, it * 30f))
                }
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