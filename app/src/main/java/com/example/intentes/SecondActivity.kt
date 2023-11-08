package com.example.intentes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class SecondActivity : AppCompatActivity() {

    private lateinit var textHello: TextView
    private lateinit var buttonGoThirdActivity: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        textHello = findViewById(R.id.textHello)
        buttonGoThirdActivity = findViewById(R.id.buttonGoThirdActivity)

        val bundle = intent.extras

        if(bundle != null){
            textHello.text = bundle.getString("saludo")
        }else{
            Toast.makeText(this@SecondActivity, "El texto esta vacio", Toast.LENGTH_LONG).show()
        }

        buttonGoThirdActivity.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }
    }
}