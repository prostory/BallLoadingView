package com.prostory.ballloading

import android.graphics.Path

interface RunningTrack {
    fun getPath(radX: Float, radY: Float, ballRadius: Float, ballSize: Int): Path
    fun getFraction(factor: Float): Float = factor
}