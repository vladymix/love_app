package com.altamirano.fabricio.vibrationlive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.altamirano.fabricio.vibrationlive.fragments.RecordVibrationFragment
import com.altamirano.fabricio.vibrationlive.fragments.SingleVibrationFragment
import com.altamirano.fabricio.vibrationlive.models.DataVibrate
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    lateinit var vibrator: Vibrator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()

        inflateFragment(SingleVibrationFragment())


      /*  vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        btnVibrate = findViewById(R.id.btnVibrate)

        btnVibrate.setOnClickListener { this.onVibrate() }

        findViewById<View>(R.id.content).setOnTouchListener(this)
        findViewById<View>(R.id.btnPlay).setOnClickListener { this.onPlayRecord() }*/
    }

    fun inflateFragment(fragment:Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
        transaction.replace(R.id.fragmentContent, fragment)
      //  transaction.addToBackStack(fragment.toString())
        transaction.commit()
    }



}
