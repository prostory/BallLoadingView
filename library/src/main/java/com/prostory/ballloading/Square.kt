package com.prostory.ballloading

import android.graphics.Path

class Square : RunningTrack {
    private val path by lazy { Path() }

    override fun getPath(radX: Float, radY: Float, ballRadius: Float, ballSize: Int): Path {
        path.reset()
        path.addRect(ballRadius, ballRadius, 2 * radX-ballRadius,
            2 * radX-ballRadius, Path.Direction.CCW)
        return path
    }
}