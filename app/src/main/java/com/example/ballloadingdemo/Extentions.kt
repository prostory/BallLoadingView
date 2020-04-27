package com.example.ballloadingdemo

import android.content.res.Resources
import android.util.TypedValue
import com.xw.repo.BubbleSeekBar

fun BubbleSeekBar.withChangeAction(action: (progress: Int, progressFloat: Float)-> Unit ) {
    onProgressChangedListener = object: BubbleSeekBar.OnProgressChangedListenerAdapter() {
        override fun getProgressOnActionUp(
            bubbleSeekBar: BubbleSeekBar?,
            progress: Int,
            progressFloat: Float
        ) {
            action(progress, progressFloat)
        }
    }
}

val Number.toDP: Float
    get() = toFloat() / TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        1f, Resources.getSystem().displayMetrics)