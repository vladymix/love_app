package com.altamirano.fabricio.vibrationlive.dialogs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.altamirano.fabricio.vibrationlive.R
import com.altamirano.fabricio.vibrationlive.ReceiverActivity
import com.altamirano.fabricio.vibrationlive.service.ApiService
import com.altamirano.fabricio.vibrationlive.service.PairResponse
import java.lang.StringBuilder

class GenerateCodeDialog : DialogFragment() {

    lateinit var idTextCode: TextView
    lateinit var progressIndicator: ProgressBar
     var codeGenerated: String=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.layout_generate_code,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.idTextCode = view.findViewById(R.id.idTextCode)
        this.progressIndicator = view.findViewById(R.id.progressIndicator)
        view.findViewById<View>(R.id.btnCode).setOnClickListener { this.onGenerateCode() }
        this.onGenerateCode()

        view.findViewById<View>(R.id.btnCopy).setOnClickListener { this.onCopy() }
    }

    private fun onCopy() {
        if(this.codeGenerated.isEmpty()){
            return
        }

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, codeGenerated)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun onGenerateCode() {
        this.progressIndicator.visibility = View.VISIBLE

        ApiService.getInstance().generateCode {
            it?.let {
                codeGenerated = it
                idTextCode.text = getCodeStyle(codeGenerated)
                checkAsync()
            }
        }
    }

    private fun checkAsync() {
        ApiService.getInstance().checkPair(codeGenerated) {
            if (this.isVisible) {
                if (it == null || it.connected == 0) {
                    checkAsync()
                } else {
                    progressIndicator.visibility = View.GONE
                    pairCorrect(it)
                }
            }
        }
    }

    private fun pairCorrect(response:PairResponse){
       val intent = Intent(this.context, ReceiverActivity::class.java)
        intent.putExtra("email", response.with_user)
        intent.putExtra("code", codeGenerated)
        startActivity(intent)
        this.dismiss()
    }

    private fun getCodeStyle(srt: String): String {
        val builder = StringBuilder()
        var index = 1
        for (item in srt) {
            builder.append(item).append(" ")
            if (index % 4 == 0) {
                builder.append("  ")
            }
            index++
        }
        return builder.toString()
    }
}