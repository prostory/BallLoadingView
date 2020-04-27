package com.prostory.ballloading

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.res.Resources
import android.graphics.Path
import android.graphics.PointF
import android.util.TypedValue

val Number.dp: Int
    get() = Math.round(
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            toFloat(), Resources.getSystem().displayMetrics
        )
    )

fun ValueAnimator.duration(t: Long): ValueAnimator = apply {
    duration = t
}

fun ValueAnimator.repeat(): ValueAnimator = apply {
    repeatCount = ValueAnimator.INFINITE
    repeatMode = ValueAnimator.RESTART
}

fun ValueAnimator.interpolator(interpolator: TimeInterpolator) = apply {
    this.interpolator = interpolator
}

fun ValueAnimator.withResetAction(action: () -> Unit) = apply {
    addListener(object: AnimatorListenerAdapter() {
        override fun onAnimationCancel(animation: Animator?) {
            action()
        }

        override fun onAnimationEnd(animation: Animator?) {
            action()
        }
    })
}

fun Path.moveTo(p: PointF) {
    moveTo(p.x, p.y)
}

fun Path.lineTo(p: PointF) {
    lineTo(p.x, p.y)
}