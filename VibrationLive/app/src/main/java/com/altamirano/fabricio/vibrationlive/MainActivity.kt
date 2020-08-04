package com.altamirano.fabricio.vibrationlive

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.altamirano.fabricio.vibrationlive.fragments.SingleVibrationFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()

        inflateFragment(SingleVibrationFragment())
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
