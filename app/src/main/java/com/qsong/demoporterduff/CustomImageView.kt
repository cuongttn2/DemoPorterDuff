package com.qsong.demoporterduff

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageView

@SuppressLint("ClickableViewAccessibility")
class CustomImageView(context: Context, attrs: AttributeSet) : ImageView(context, attrs) {
    private val path = Path()
    private val paint = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 8f
    }
    private var drawCanvas: Canvas? = null
    private lateinit var drawingBitmap: Bitmap

    init {
        setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    path.moveTo(event.x, event.y)
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    path.lineTo(event.x, event.y)
                    invalidate()
                    true
                }

                else -> false
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCanvas = canvas
        canvas.drawPath(path, paint)
    }


    fun getMaskedBitmap(originalBitmap: Bitmap): Bitmap {
        val width = originalBitmap.width
        val height = originalBitmap.height

        val maskedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(maskedBitmap)

        canvas.drawColor(Color.TRANSPARENT)

        val maskPaint = Paint().apply {
            style = Paint.Style.FILL
            color = Color.BLACK
            isAntiAlias = true
        }

        canvas.drawPath(path, maskPaint)

        val imagePaint = Paint().apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        }

        canvas.drawBitmap(originalBitmap, 0f, 0f, imagePaint)

        return maskedBitmap
    }

}