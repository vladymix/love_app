package com.altamirano.fabricio.vibrationlive.fragments

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.altamirano.fabricio.vibrationlive.R
import com.altamirano.fabricio.vibrationlive.models.DataVibrate
import java.lang.Exception


class RecordVibrationFragment : Fragment(), View.OnTouchListener {
    var vibrator: Vibrator? = null
    lateinit var btnVibrate: Button
    var starRecord = false
    var listSave = ArrayList<DataVibrate>()
    var elpase = 0L
    var motion = 36000L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_only_vibration, container, false)

        btnVibrate = root.findViewById(R.id.btnVibrate)

        btnVibrate.setOnClickListener { this.onVibrate() }

        root.findViewById<View>(R.id.content).setOnTouchListener(this)
        root.findViewById<View>(R.id.btnPlay).setOnClickListener { this.onPlayRecord() }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vibrator = this.activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    private fun onPlayRecord() {
        vibrator?.cancel()

        val list = LongArray(listSave.size)

        for (i in 0 until listSave.size) {
            list[i] = listSave[i].time
        }

        vibrator?.vibrate(list, -1)
    }

    private fun onVibrate() {
        starRecord = !starRecord
        if (starRecord) {
            btnVibrate.text = "Parar"
            listSave = ArrayList()
            elpase = System.currentTimeMillis()
        } else {
            btnVibrate.text = "Grabar"
            val lastTime = System.currentTimeMillis()
            val duration = lastTime - elpase
            listSave.add(DataVibrate(false, duration))
            elpase = lastTime
        }
        //  vibrator.vibrate(500L)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        event?.run {
            val x = this.getY().toLong()
            if (this.getAction() == MotionEvent.ACTION_UP) {
                vibrator?.cancel()

                val lastTime = System.currentTimeMillis()
                val duration = lastTime - elpase
                listSave.add(DataVibrate(true, duration))
                elpase = lastTime

            } else if (this.getAction() == MotionEvent.ACTION_DOWN) {
                try {
                    vibrator?.vibrate(motion)
                    val lastTime = System.currentTimeMillis()
                    val duration = lastTime - elpase
                    listSave.add(DataVibrate(false, duration))
                    elpase = lastTime

                } catch (ex: Exception) {
                }
            } else if (this.action == MotionEvent.ACTION_MOVE) {
                Log.i("TAG", "touched move $x")

            } else {
            }
        }
        return true
    }


}
