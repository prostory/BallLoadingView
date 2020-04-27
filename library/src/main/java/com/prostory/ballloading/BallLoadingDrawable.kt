package com.prostory.ballloading

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.animation.Interpolator
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi

class BallLoadingDrawable(
    private val colors: IntArray,
    ballSize: Int = colors.size,
    minRadius: Float = 5.dp.toFloat(),
    maxRadius: Float = minRadius,
    moveDuration: Long = 5000,
    resizeDuration: Long = 2000,
    interpolator: Interpolator = Interpolations.LINEAR,
    track: RunningTrack = Infinite()
): Drawable() {
    private val paint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
        }
    }

    private var animator = AnimatorSet()

    var ballSize: Int = ballSize
        set(value) {
            if (value != field) {
                field = value
                balls = (0 until ballSize).map {
                    Ball(
                        radius = maxRadius,
                        color = colors[it % colors.size]
                    )
                }
                reset()
            }
        }

    var minRadius: Float = minRadius
        set(value) {
            if (value != field) {
                field = value
                reset()
            }
        }

    var maxRadius: Float = maxRadius
        set(value) {
            if (value != field) {
                field = value
                reset()
            }
        }

    var moveDuration: Long = moveDuration
        set(value) {
            if (value != field) {
                field = value
                reset()
            }
        }

    var resizeDuration: Long = resizeDuration
        set(value) {
            if (value != field) {
                field = value
                reset()
            }
        }

    var interpolator: Interpolator = interpolator
        set(value) {
            if (value != field) {
                field = value
                animator.cancel()
                animator.interpolator = value
                animator.start()
            }
        }

    var track: RunningTrack = track
        set(value) {
            if (value != field) {
                field = value
                reset()
            }
        }

    data class Ball(
        var x: Float = 0f,
        var y: Float = 0f,
        var radius: Float = 0f,
        @ColorInt val color: Int = Color.WHITE) {
        fun draw(canvas: Canvas, paint: Paint) {
            canvas.drawCircle(x, y, radius, paint.also { it.color = color })
        }
    }

    private var balls = (0 until ballSize).map {
        Ball(
            radius = maxRadius,
            color = colors[it % colors.size]
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBoundsChange(bounds: Rect?) {
        super.onBoundsChange(bounds)
        bounds?.also {
            updateAnimator()
        }
    }

    override fun draw(canvas: Canvas) {
        balls.forEach {
            it.draw(canvas, paint)
        }
    }

    fun start() {
        if (!animator.isRunning) {
            animator.start()
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun cancel() {
        if (animator.isRunning) {
            animator.pause()
            animator.cancel()
        }
    }

    private fun updateAnimator() {
        val path = track.getPath(bounds.exactCenterX(), bounds.exactCenterY(), maxRadius, ballSize)
        val keyframes = PathKeyframes(path)
        val xFrames = keyframes.createXFloatKeyframes()
        val yFrames = keyframes.createYFloatKeyframes()

        val ballsCount = ballSize.toFloat()

        animator.play(
            ValueAnimator.ofFloat(0f, 1f).apply {
                withResetAction {
                    balls.forEachIndexed {index, ball->
                        val fraction = track.getFraction(index/ballsCount)
                        ball.x = xFrames.getFloatValue(fraction)
                        ball.y = yFrames.getFloatValue(fraction)
                    }
                }

                addUpdateListener {
                    synchronized(this) {
                        balls.forEachIndexed {index, ball->
                            val fraction = track.getFraction((index/ballsCount + it.animatedFraction) % 1f)

                            ball.x = xFrames.getFloatValue(fraction)
                            ball.y = yFrames.getFloatValue(fraction)
                        }
                        invalidateSelf()
                    }
                }
                duration(moveDuration)
                repeat()
            }
        ).apply {
            if (maxRadius != minRadius) {
                with(ValueAnimator.ofFloat(0f, 1f).apply {
                    fun getFraction(fraction: Float): Float =
                        when {
                            fraction < 0.5f-> 1 - fraction * 2
                            else-> fraction * 2 - 1
                        }
                    withResetAction {
                        balls.forEachIndexed {index, ball->
                            ball.radius = minRadius + getFraction(index/ballsCount) * (maxRadius - minRadius)
                        }
                    }

                    addUpdateListener {
                        synchronized(this) {
                            balls.forEachIndexed {index, ball->
                                ball.radius = minRadius + getFraction((index/ballsCount + it.animatedFraction) % 1f) *
                                        (maxRadius - minRadius)
                            }
                            invalidateSelf()
                        }
                    }
                    duration(resizeDuration)
                    repeat()
                })
            }
        }
        animator.interpolator = interpolator

        start()
    }

    private fun reset() {
        animator.cancel()
        animator = AnimatorSet()
        updateAnimator()
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }
}