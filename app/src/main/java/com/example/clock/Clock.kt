package com.example.clock

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class Clock @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var radius = 0.0f
    private var hourHandLength = 95f
    private var minuteHandLength = 125f
    private var secondHandLength = 150f

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 20f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        //super.onSizeChanged(w, h, oldw, oldh)
        radius = (min(width, height) / 2.0 * 0.8).toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        val hours = Calendar.getInstance().get(Calendar.HOUR)
        val minutes = Calendar.getInstance().get(Calendar.MINUTE)
        val seconds = Calendar.getInstance().get(Calendar.SECOND)

        if (canvas != null) {
            val tWidth = width / 2
            val tHeight = height / 2
            //draw circle
            canvas.drawCircle(tWidth.toFloat(), tHeight.toFloat(), radius, paint)
            //draw scale
            for (i in 1..12) {
                val angle = Math.PI / 2 + i * (Math.PI / 6)

                canvas.drawLine(
                    (radius * cos(angle)).toFloat() + tWidth,
                    (radius * sin(angle)).toFloat() + tHeight,
                    ((radius * 0.85) * cos(angle)).toFloat() + tWidth,
                    ((radius * 0.85) * sin(angle)).toFloat() + tHeight,
                    paint
                )
            }
            //draw the hour hand
            val hAngle = Math.PI * (hours + minutes / 60.0) * 5f / 30 - Math.PI / 2;
            canvas.drawLine(
                tWidth.toFloat(),
                tHeight.toFloat(),
                (tWidth + cos(hAngle) * hourHandLength).toFloat(),
                (tHeight + sin(hAngle) * hourHandLength).toFloat(),
                paint
            )
            //draw the minute hand
            val mAngle = Math.PI * minutes / 30 - Math.PI / 2
            canvas.drawLine(
                tWidth.toFloat(),
                tHeight.toFloat(),
                (tWidth + cos(mAngle) * minuteHandLength).toFloat(),
                (tHeight + sin(mAngle) * minuteHandLength).toFloat(),
                paint
            )
            //draw the second hand
            val sAngle = Math.PI * seconds / 30 - Math.PI / 2
            canvas.drawLine(
                tWidth.toFloat(),
                tHeight.toFloat(),
                (tWidth + cos(sAngle) * secondHandLength).toFloat(),
                (tHeight + sin(sAngle) * secondHandLength).toFloat(),
                paint
            )
            invalidate()
            //paint.color = Color.BLUE
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}
