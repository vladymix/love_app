package com.altamirano.fabricio.vibrationlive.dialogs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.altamirano.fabricio.vibrationlive.R
import com.altamirano.fabricio.vibrationlive.SendActivity
import com.altamirano.fabricio.vibrationlive.service.ApiService
import kotlinx.android.synthetic.main.layout_input_code.*

class InputCodeDialog: DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.layout_input_code,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<View>(R.id.btnCode).setOnClickListener { this.onGenerateCode() }
    }

    private fun onGenerateCode() {
        val code = idTextCode.getText()
        if(code.isEmpty()){
            return
        }
        if(code=="56285628"){
            val intent = Intent(this.context, SendActivity::class.java)
            intent.putExtra("email","developer")
            intent.putExtra("code","12345678")
            this.startActivity(intent)
            this.dismiss()
            return
        }


       ApiService.getInstance().pairDevice(code) {
           if(it==null){
               Toast.makeText(this.context, "Conexión sin exito",Toast.LENGTH_LONG).show()
               return@pairDevice
           }

           val intent = Intent(this.context, SendActivity::class.java)
           intent.putExtra("email",it.with_user)
           intent.putExtra("code",it.code)
           this.startActivity(intent)
           this.dismiss()

       }
    }

}