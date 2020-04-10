package com.altamirano.fabricio.vibrationlive

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.widget.Toast
import com.altamirano.fabricio.vibrationlive.service.ApiService
import kotlinx.android.synthetic.main.activity_receiver.*

class ReceiverActivity : AppCompatActivity() {

    lateinit var code: String
    var listForever = ArrayList<Runnable>()
    val handler = Handler()
    var vibrator: Vibrator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        this.vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        intent.extras?.getString("email")?.let {
            targetUser.text = it
            if (it.isEmpty()) {
                finish()
            }
        }

        intent.extras?.getString("code")?.let {
            this.code = it
        }

        checkAsync()

        this.btnRetry.setOnClickListener { this.onRetry() }

    }

    private fun onRetry() {
        checkAsync()
    }

    private fun checkAsync() {
        ApiService.getInstance().checkPair(this.code) { respose ->
            if (!this.isDestroyed) {
                respose?.let {
                    changeValue(it.is_active)
                    if (it.connected == 1) {
                        checkAsync()
                    } else {
                        Toast.makeText(this, "Lost connection", Toast.LENGTH_LONG).show()
                        this.finish()
                    }

                }
            }
        }
    }

    var oldValue = false

    private fun onChangeValue(newValue: Boolean) {
        if (newValue == this.oldValue) {
            return
        }
        this.oldValue = newValue

        if (this.oldValue) {
            imgVibrate.setColorFilter(Color.BLUE)
            this.vibrateForever()
        } else {
            imgVibrate.setColorFilter(Color.GRAY)
            this.stopForeverVibration()
        }
    }

    private fun changeValue(is_Active: Int?) {
        is_Active?.let {
            Log.i("Call ❤️", "value is active:$it")
            onChangeValue(it == 1)
        }
    }

    private fun stopForeverVibration() {
        this.vibrator?.cancel()
        listForever.forEach {
            handler.removeCallbacks(it)
        }
    }

    override fun onDestroy() {
        this.stopForeverVibration()
        super.onDestroy()
    }

    private fun vibrateForever() {
        this.stopForeverVibration()

        listForever.clear()

        var post: Long = 8000
        this.vibrator?.vibrate(8000)

        while (post < 600000) {

            val tasktRunnable = Runnable {
                Log.i("Vibrator", "Task tasktRunnable")
                this.vibrator?.vibrate(8000)
            }
            listForever.add(tasktRunnable)

            handler.postDelayed(tasktRunnable, post)
            Log.i("Vibrator", "Task to $post next milisecons")
            post += 8000
        }
    }

}
