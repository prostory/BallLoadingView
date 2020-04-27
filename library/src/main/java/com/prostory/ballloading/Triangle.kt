package com.prostory.ballloading

import android.graphics.Path

class Triangle: RunningTrack {
    private val path by lazy { Path() }

    override fun getPath(radX: Float, radY: Float, ballRadius: Float, ballSize: Int): Path {
        path.reset()
        path.moveTo(radX, ballRadius)
        path.lineTo(2 * radX - ballRadius, 2 * radY - ballRadius)
        path.lineTo(ballRadius, 2 * radY - ballRadius)
        path.close()
        return path
    }
}