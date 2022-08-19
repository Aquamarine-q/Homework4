package com.example.clock

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import java.util.*
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class Clock @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var hourHandWidth = 0f
    private var hourHandColor = 0
    private var minuteHandWidth = 0f
    private var minuteHandColor = 0
    private var secondHandWidth = 0f
    private var secondHandColor = 0

    private var radius = 0.0f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        context.withStyledAttributes(attrs, R.styleable.Clock) {
            hourHandWidth = getDimension(R.styleable.Clock_hourHandWidth, 20f)
            hourHandColor = getColor(R.styleable.Clock_hourHandColor, Color.BLACK)
            minuteHandWidth = getDimension(R.styleable.Clock_minuteHandWidth, 15f)
            minuteHandColor =getColor(R.styleable.Clock_minuteHandColor, Color.RED)
            secondHandWidth = getDimension(R.styleable.Clock_secondHandWidth, 8f)
            secondHandColor=getColor(R.styleable.Clock_secondHandColor, Color.BLUE)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        val hours = Calendar.getInstance().get(Calendar.HOUR)
        val minutes = Calendar.getInstance().get(Calendar.MINUTE)
        val seconds = Calendar.getInstance().get(Calendar.SECOND)

        if (canvas != null) {
            val tWidth = width / 2
            val tHeight = height / 2
            //draw circle
            paint.apply {
                color = Color.BLACK
                strokeWidth = 20f
                style = Paint.Style.STROKE
            }
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
            paint.apply {
                color = hourHandColor
                strokeWidth = hourHandWidth
            }
            val hAngle = Math.PI * (hours + minutes / 60.0) * 5f / 30 - Math.PI / 2
            canvas.drawLine(
                tWidth.toFloat(),
                tHeight.toFloat(),
                (tWidth + cos(hAngle) * radius * 0.33).toFloat(),
                (tHeight + sin(hAngle) * radius * 0.33).toFloat(),
                paint
            )
            //draw the minute hand
            paint.apply {
                color = minuteHandColor
                strokeWidth = minuteHandWidth
            }
            val mAngle = Math.PI * minutes / 30 - Math.PI / 2
            canvas.drawLine(
                tWidth.toFloat(),
                tHeight.toFloat(),
                (tWidth + cos(mAngle) * radius * 0.5).toFloat(),
                (tHeight + sin(mAngle) * radius * 0.5).toFloat(),
                paint
            )
            //draw the second hand
            paint.apply {
                color = secondHandColor
                strokeWidth = secondHandWidth
            }
            val sAngle = Math.PI * seconds / 30 - Math.PI / 2
            canvas.drawLine(
                tWidth.toFloat(),
                tHeight.toFloat(),
                (tWidth + cos(sAngle) * radius * 0.67).toFloat(),
                (tHeight + sin(sAngle) * radius * 0.67).toFloat(),
                paint
            )
            //draw the centre of clock
            paint.apply {
                color = Color.BLACK
                style = Paint.Style.FILL
            }
            canvas.drawCircle(tWidth.toFloat(), tHeight.toFloat(), radius * 0.04f, paint)

            invalidate()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        //super.onSizeChanged(w, h, oldw, oldh)
        radius = (min(width, height) / 2.0 * 0.8).toFloat()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}
