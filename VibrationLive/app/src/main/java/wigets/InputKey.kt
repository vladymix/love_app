package wigets

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import com.altamirano.fabricio.vibrationlive.R
import java.lang.StringBuilder

class InputKey(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs),
    TextWatcher {

    private val inputValues: EditText
    private var oldCode = ""
    private var changeByCode = false


    init {
        View.inflate(this.context, R.layout.layout_code, this)
        this.inputValues = this.findViewById(R.id.inputValues)

        this.inputValues.addTextChangedListener(this)
    }

    override fun afterTextChanged(s: Editable?) {
        changeByCode = false
        inputValues.setSelection(inputValues.text.length)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        if (s != null) {
            if (s.length < oldCode.length) {
                oldCode = s.toString()
                return
            }
        }

        if (!changeByCode) {
            setText(s.toString())
        }

    }

    fun setText(code: String) {
        changeByCode = true
        val clean = code.replace(" ", "")
        oldCode = getCodeStyle(clean)
        inputValues.setText(oldCode)
    }

    fun getText(): String {
        return inputValues.text.toString().replace(" ", "")
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