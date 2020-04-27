package com.example.ballloadingdemo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.prostory.ballloading.Interpolations
import com.prostory.ballloading.dp
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        ballSize.setProgress(loadingView.ballSize.toFloat())
        minRadius.setProgress(loadingView.minRadius.toDP)
        maxRadius.setProgress(loadingView.maxRadius.toDP)
        rotateDuration.setProgress(loadingView.rotateDuration / 1000f)
        scaleDuration.setProgress(loadingView.scaleDuration / 1000f)

        ballSize.withChangeAction { progress, _ ->
            loadingView.ballSize = progress
        }
        minRadius.withChangeAction { progress, _ ->
            loadingView.minRadius = progress.dp.toFloat()
        }
        maxRadius.withChangeAction { progress, _ ->
            loadingView.maxRadius = progress.dp.toFloat()
        }
        rotateDuration.withChangeAction { _, progressFloat ->
            loadingView.rotateDuration = (progressFloat * 1000).toLong()
        }
        scaleDuration.withChangeAction { _, progressFloat ->
            loadingView.rotateDuration = (progressFloat * 1000).toLong()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.order < 6) {
            loadingView.trackType = item.order
        } else if (item.order == 100){
            loadingView.interpolator = when (item.itemId) {
                R.id.interpolator_accelerate-> Interpolations.ACCELERATE
                R.id.interpolator_decelerate-> Interpolations.DECELERATE
                R.id.interpolator_overshoot-> Interpolations.OVERSHOOT
                R.id.interpolator_fast_out_slow_in-> Interpolations.FAST_OUT_SLOW_IN
                else-> Interpolations.LINEAR
            }
        }

        return true
    }
}
