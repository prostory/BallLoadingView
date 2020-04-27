package com.prostory.ballloading

import android.view.animation.*
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

object Interpolations {
    val LINEAR by lazy { LinearInterpolator() }
    val ACCELERATE by lazy {
        AccelerateInterpolator()
    }
    val DECELERATE by lazy {
        DecelerateInterpolator()
    }
    val OVERSHOOT by lazy {
        OvershootInterpolator()
    }
    val FAST_OUT_SLOW_IN by lazy {
        FastOutSlowInInterpolator()
    }
}