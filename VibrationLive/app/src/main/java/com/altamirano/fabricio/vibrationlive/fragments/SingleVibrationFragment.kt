package com.altamirano.fabricio.vibrationlive.fragments

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.*
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.altamirano.fabricio.vibrationlive.AppUtils
import com.altamirano.fabricio.vibrationlive.R
import com.altamirano.fabricio.vibrationlive.dialogs.ChooseModeDialog

@SuppressLint("SetTextI18n")
class SingleVibrationFragment : Fragment() {

    private lateinit var tvLevel: TextView
    private lateinit var imgBattery: ImageView
    private lateinit var mBatteryManager: BatteryStatusChange
    private lateinit var btnPower: CheckBox
    private lateinit var btnTow: CheckBox
    private lateinit var btnEquals: CheckBox
    private lateinit var btnBest: CheckBox

    var listForever =ArrayList<Runnable>()
    val handler = Handler()
    var vibrator: Vibrator? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_single_vibration, container, false)

        tvLevel = root.findViewById(R.id.tvLevel)
        imgBattery = root.findViewById(R.id.imgBattery)
        btnBest = root.findViewById(R.id.btnBest)
        btnPower = root.findViewById(R.id.btnPower)
        btnTow = root.findViewById(R.id.btnTow)
        btnEquals = root.findViewById(R.id.btnEquals)

        root.findViewById<View>(R.id.btnShare).setOnClickListener { this.onContinueShare() }

        btnBest.setOnCheckedChangeListener { buttonView, isChecked ->
            this.onCheckedChangeListener(
                buttonView,
                isChecked
            )
        }
        btnPower.setOnCheckedChangeListener { buttonView, isChecked ->
            this.onCheckedChangeListener(
                buttonView,
                isChecked
            )
        }
        btnTow.setOnCheckedChangeListener { buttonView, isChecked ->
            this.onCheckedChangeListener(
                buttonView,
                isChecked
            )
        }
        btnEquals.setOnCheckedChangeListener { buttonView, isChecked ->
            this.onCheckedChangeListener(
                buttonView,
                isChecked
            )
        }

        mBatteryManager = BatteryStatusChange()

        return root

    }

    private fun onContinueShare() {

        ChooseModeDialog()
            .show(fragmentManager!!,"")

      //  fragmentManager?.beginTransaction()?.replace(R.id.fragmentContent, RecordVibrationFragment())?.commit()
    }


    private fun onCheckedChangeListener(button: View, checked: Boolean) {

        if (!checked) {
            this.stopForeverVibration()
            this.vibrator?.cancel()
            return
        }

        this.unChecked(btnPower, button.id)
        this.unChecked(btnTow, button.id)
        this.unChecked(btnEquals, button.id)
        this.unChecked(btnBest, button.id)

        if (checked) {
            when (button.id) {
                R.id.btnPower -> {
                    vibrateForever()
                }
                R.id.btnEquals -> {
                    this.vibrator?.vibrate(AppUtils.getEquals(), 1)
                }
                R.id.btnTow -> {
                    this.vibrator?.vibrate(AppUtils.getTow(), 1)
                }
                R.id.btnBest -> {
                    this.vibrator?.vibrate(AppUtils.getBest(), 1)
                }
            }
        }
    }

    fun vibrateForever() {
        this.stopForeverVibration()

        listForever.clear()

        var post: Long = 8000
        this.vibrator?.vibrate(8000)

        while (post < 600000) {

            val tasktRunnable = Runnable {
                Log.i("Vibrator","Task tasktRunnable")
                this.vibrator?.vibrate(8000)
            }
            listForever.add(tasktRunnable)

            handler.postDelayed(tasktRunnable, post)
            Log.i("Vibrator","Task to $post next milisecons")
            post+=8000
        }
    }

    private fun stopForeverVibration() {
        listForever.forEach {
            handler.removeCallbacks(it)
        }
    }

    fun unChecked(btn: CheckBox, idCurrent: Int) {
        if (idCurrent != btn.id) {
            if (btn.isChecked) {
                btn.isChecked = false
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.activity?.registerReceiver(
            this.mBatteryManager,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        )
        this.vibrator = this.activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    override fun onDestroy() {
        this.activity?.unregisterReceiver(this.mBatteryManager)
        super.onDestroy()
    }


    inner class BatteryStatusChange : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            val batteryPct: Float? = intent?.let {
                val level: Int = it.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale: Int = it.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                (level / scale.toFloat()) * 100
            }
            //val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
            batteryPct?.let { value ->
                if (value <= 30) {
                    imgBattery.setImageResource(R.drawable.bat_low)
                } else if (value > 30 && value <= 50) {
                    imgBattery.setImageResource(R.drawable.bat_medium)
                } else if (value > 50 && value <= 85) {
                    imgBattery.setImageResource(R.drawable.bat_normal)
                } else {
                    imgBattery.setImageResource(R.drawable.bat_full)
                }
                tvLevel.text = "$value %"
            }
        }
    }
}
