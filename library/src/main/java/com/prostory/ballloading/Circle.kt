package com.prostory.ballloading

import android.graphics.Path

class Circle : RunningTrack {
    private val path by lazy { Path() }

    override fun getPath(radX: Float, radY: Float, ballRadius: Float, ballSize: Int): Path {
        val radius = (radX).coerceAtMost(radY) - ballRadius
        path.reset()
        path.addOval(radX - radius, radY - radius,
            radX + radius, radY + radius, Path.Direction.CCW)
        return path
    }
}