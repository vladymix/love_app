package com.altamirano.fabricio.vibrationlive.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.altamirano.fabricio.vibrationlive.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_button_dialog_mode.*


class ChooseModeDialog : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_button_dialog_mode,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.btnRecibir.setOnClickListener { this.onReciber() }
        this.btnDar.setOnClickListener { this.onDar() }
    }

    private fun onDar() {
        val dialog = InputCodeDialog()
        dialog.show(fragmentManager!!, "")
        this.dismiss()
    }

    private fun onReciber() {
        val dialog = GenerateCodeDialog()
        dialog.show(fragmentManager!!, "")
        this.dismiss()
    }


}
