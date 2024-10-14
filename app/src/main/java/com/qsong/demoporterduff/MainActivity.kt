package com.qsong.demoporterduff

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var customImageView: CustomImageView
    private lateinit var originalBitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        customImageView = findViewById(R.id.customImageView)

        originalBitmap = BitmapFactory.decodeResource(resources, R.drawable.sample_image)
        customImageView.setImageBitmap(originalBitmap)

        findViewById<Button>(R.id.triggerButton).setOnClickListener {
            val maskedBitmap = customImageView.getMaskedBitmap(originalBitmap)

            findViewById<ImageView>(R.id.resultImageView).setImageBitmap(maskedBitmap)
        }

    }
}