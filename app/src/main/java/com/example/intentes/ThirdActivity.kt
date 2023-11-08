package com.example.intentes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class ThirdActivity : AppCompatActivity() {

    private var buttonCamera: ImageButton? = null
    private val PICTURE_FROM_CAMERA = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        buttonCamera = findViewById(R.id.buttonCamera)

        buttonCamera!!.setOnClickListener {
            val intentCamera = Intent("android.media.action.IMAGE_CAPTURE")
            startActivityForResult(intentCamera, PICTURE_FROM_CAMERA)
        }
    }
}