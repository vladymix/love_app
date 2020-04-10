package com.altamirano.fabricio.vibrationlive

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View

class BatteryWiget(context: Context?,attributes: AttributeSet?) : View(context,attributes) {

    val level: Int = 100

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val levelWith = (measuredWidth - spaces(this.level)) /100

        Log.i("levelWith", levelWith.toString())
        val end = (level*2) - 2

        try {
            for (i in 0..end) {
                if (i % 2 == 0) {
                    val color:Int = when (level) {
                        in 31..50 -> {
                            Color.rgb(243,128,24)
                        }
                        in 51..85 -> {
                            Color.rgb(124,190,49)
                        }
                        else -> {
                            Color.GREEN
                        }
                    }
                    var spa =i*levelWith

                    if(spa!=0){
                        spa +=10
                    }
                    drawLevel(canvas,measuredHeight,levelWith,spa, color)
                } else {
                    drawSpace(canvas, measuredHeight,5,i * levelWith)
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    // Ancho de espacios = 10
    private fun spaces(level:Int):Int{
      val numberSpaces =  level-1
      return  numberSpaces * 5
    }

    private fun drawSpace(canvas: Canvas?, height: Int, width: Int, space: Int) {
        try {
            val paint = Paint()
            paint.color = Color.BLACK
            paint.strokeWidth = 0f
            val rec = Rect(space, 0, space+width, height)
            canvas?.drawRect(rec, paint)
        } catch (ex: java.lang.Exception) {

        }
    }


    private fun drawLevel(canvas: Canvas?, height: Int, width: Int, space: Int, color: Int) {
        try {
            val paint = Paint()
            paint.color = color
            paint.strokeWidth = 0f
            val rec = Rect(space, 0, space+width, height)
            canvas?.drawRect(rec, paint)
        } catch (ex: java.lang.Exception) {

        }
    }
}