package com.prostory.ballloading

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import androidx.annotation.AttrRes
import androidx.annotation.RequiresApi

@Suppress("unused")
class BallLoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    companion object {
        const val INFINITE: Int = 0
        const val SQUARE: Int = 0
        const val TRIANGLE: Int = 0
        const val CIRCLE: Int = 0
        const val STAR: Int = 0
        const val DIAMOND: Int = 0

        private val tracks by lazy {
            arrayOf(Infinite(), Square(), Triangle(), Circle(), Star(), Diamond())
        }
    }

    var trackType: Int = 0
        set(value) {
            field = value
            loadingDrawable?.track = tracks[field]
        }

    init {
        @SuppressLint("CustomViewStyleable")
        val attr =
            context.obtainStyledAttributes(attrs, R.styleable.BallView)
        val colorsResId = attr.getResourceId(R.styleable.BallView_colors, 0)
        val colors = if (colorsResId != 0) {
            resources.getIntArray(colorsResId)
        } else {
            intArrayOf(Color.GRAY)
        }
        val ballSize = attr.getInt(R.styleable.BallView_ballSize, colors.size)
        val minRadius = attr.getDimension(R.styleable.BallView_minRadius, 5.dp.toFloat())
        val maxRadius = attr.getDimension(R.styleable.BallView_maxRadius, minRadius)
        val moveDuration = attr.getInt(R.styleable.BallView_moveDuration, 5000).toLong()
        val resizeDuration = attr.getInt(R.styleable.BallView_resizeDuration, 2000).toLong()
        val interpolatorResId = attr.getResourceId(R.styleable.BallView_animationInterpolator, 0)
        val interpolator = if (interpolatorResId != 0) {
            AnimationUtils.loadInterpolator(context, interpolatorResId)
        } else {
            Interpolations.LINEAR
        }
        trackType = attr.getInt(R.styleable.BallView_trackType, 0)
        val track = tracks[trackType]

        background = BallLoadingDrawable(
            colors, ballSize, minRadius, maxRadius,
            moveDuration, resizeDuration, interpolator, track
        )
        attr.recycle()
    }

    val loadingDrawable: BallLoadingDrawable?
        get() = when (val background = background) {
            is BallLoadingDrawable-> background
            else-> null
        }

    var ballSize: Int
        get() = loadingDrawable?.ballSize?: 0
        set(value) {
            loadingDrawable?.ballSize = value
        }

    var minRadius: Float
        get() = loadingDrawable?.minRadius?: 0f
        set(value) {
            loadingDrawable?.minRadius = value
        }

    var maxRadius: Float
        get() = loadingDrawable?.maxRadius?: 0f
        set(value) {
            loadingDrawable?.maxRadius = value
        }

    var moveDuration: Long
        get() = loadingDrawable?.moveDuration?: 0L
        set(value) {
            loadingDrawable?.moveDuration = value
        }

    var resizeDuration: Long
        get() = loadingDrawable?.resizeDuration?: 0L
        set(value) {
            loadingDrawable?.resizeDuration = value
        }

    var interpolator: Interpolator
        get() = loadingDrawable?.interpolator?: Interpolations.LINEAR
        set(value) {
            loadingDrawable?.interpolator = value
        }

    var track: RunningTrack?
        get() = loadingDrawable?.track
        set(value) {
            value?.let {
                loadingDrawable?.track = it
            }
        }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        when (val background = background) {
            is BallLoadingDrawable ->
                try {
                    if (visibility == VISIBLE) {
                        background.start()
                    } else {
                        background.cancel()
                    }
                } catch (e: Throwable) {
                }
        }
    }
}