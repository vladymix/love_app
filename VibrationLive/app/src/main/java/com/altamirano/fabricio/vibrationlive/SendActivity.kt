package com.altamirano.fabricio.vibrationlive

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.altamirano.fabricio.vibrationlive.service.ApiService
import kotlinx.android.synthetic.main.activity_send.*

class SendActivity : AppCompatActivity(), View.OnTouchListener {

    private var apiService = ApiService.getInstance()
    private var oldValue = false
    private lateinit var code:String
    var vibrator: Vibrator? = null
    var motion = 36000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send)

        intent.extras?.getString("email")?.let {
            if (it.isEmpty()) {
                finish()
            }
            this.targetUser.text = it
        }

        intent.extras?.getString("code")?.let {
            this.code = it
        }

        findViewById<View>(R.id.content).setOnTouchListener(this)

        this.vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        event?.run {
            if (this.action == MotionEvent.ACTION_UP) {
                changeValue(false)

            } else if (this.action == MotionEvent.ACTION_DOWN) {
                changeValue(true)
            }
        }
        return true
    }

    private fun changeValue(newValue:Boolean){
        if(newValue == oldValue){
            return
        }

        this.oldValue = newValue
        if(oldValue){
            imgVibrate.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary))
            this.vibrate()
        }else{
            imgVibrate.setColorFilter(Color.GRAY)
            vibrator?.cancel()
        }

        apiService.sendMessage(code, when(this.oldValue){
            true->1
            else-> 0})
    }

    private fun vibrate (){
        if(swithVibrate.isChecked){
            vibrator?.vibrate(motion)
        }
    }
}
