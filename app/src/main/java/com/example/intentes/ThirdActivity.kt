package com.example.intentes

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import android.Manifest

class ThirdActivity : AppCompatActivity() {

    private lateinit var buttonCamera: ImageButton
    private lateinit var buttonWeb: ImageButton
    private lateinit var buttonContact: ImageButton
    private lateinit var buttonEmail: ImageButton
    private lateinit var buttonEmailComplete: ImageButton
    private lateinit var buttonPhone: ImageButton
    private lateinit var buttonPhoneCall: ImageButton
    private lateinit var editTextWeb: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextEmailComplete: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editTextPhoneCall: EditText
    private val PICTURE_FROM_CAMERA = 50
    private val PHONE_CALL_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        buttonCamera = findViewById(R.id.buttonCamera)
        buttonWeb = findViewById(R.id.buttonWeb)
        editTextWeb = findViewById(R.id.editTextWeb)
        buttonContact = findViewById(R.id.buttonContact)
        buttonEmail = findViewById(R.id.buttonEmail)
        editTextEmail = findViewById(R.id.editTextEmail)
        buttonEmailComplete = findViewById(R.id.buttonEmailComplete)
        editTextEmailComplete = findViewById(R.id.editTextEmailComplete)
        buttonPhone = findViewById(R.id.buttonPhone)
        editTextPhone = findViewById(R.id.editTextPhone)
        buttonPhoneCall = findViewById(R.id.buttonPhoneCall)
        editTextPhoneCall = findViewById(R.id.editTextPhoneCall)

        buttonCamera.setOnClickListener {
            val intentCamera = Intent("android.media.action.IMAGE_CAPTURE")
            startActivityForResult(intentCamera, PICTURE_FROM_CAMERA)
        }

        buttonWeb.setOnClickListener {
            val url = editTextWeb.text.toString()
            if(!url.isEmpty()){
                val intentWeb = Intent()
                intentWeb.action = Intent.ACTION_VIEW
                intentWeb.data = Uri.parse("http://$url")
                startActivity(intentWeb)
            }
        }

        buttonContact.setOnClickListener {
            val intentContact = Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"))
            startActivity(intentContact)
        }

        buttonEmail.setOnClickListener {
            val email = editTextEmail.text.toString()
            if(!email.isEmpty()){
                val intentEmail = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
                startActivity(intentEmail)
            }
        }

        buttonEmailComplete.setOnClickListener {
            val emailComplete = editTextEmailComplete.text.toString()
            if(!emailComplete.isEmpty()){
                val intentEmailComplete = Intent(Intent.ACTION_SEND, Uri.parse(emailComplete))
                intentEmailComplete.type = "plain/text"
                intentEmailComplete.putExtra(Intent.EXTRA_SUBJECT, "Titulo")
                intentEmailComplete.putExtra(Intent.EXTRA_TEXT, "Hola, esto es la descripción del correo")
                intentEmailComplete.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailComplete, emailComplete))
                startActivity(Intent.createChooser(intentEmailComplete, "Elige cliente de correo"))
            }
        }

        buttonPhone.setOnClickListener {
            try {
                val phone = editTextPhone.text.toString()
                if (!phone.isEmpty()) {
                    val intentPhone = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${phone}"))
                    startActivity(intentPhone)
                }
            } catch (e: Exception){
                Log.d("ErrorAplicacion", e.message.toString())
                Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
            }
        }

        buttonPhoneCall.setOnClickListener {
            val phoneCall = editTextPhoneCall.text.toString()
            if(!phoneCall.isEmpty()){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkPermission(Manifest.permission.CALL_PHONE)) {
                        val intentPhoneCall =
                            Intent(Intent.ACTION_CALL, Uri.parse("tel:${phoneCall}"))
                        if (ActivityCompat.checkSelfPermission(
                                this@ThirdActivity,
                                Manifest.permission.CALL_PHONE
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            return@setOnClickListener
                        } else {
                            startActivity(intentPhoneCall)
                        }
                    }else{
                        if(!shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                            requestPermissions(
                                arrayOf(Manifest.permission.CALL_PHONE),
                                    PHONE_CALL_CODE
                            )
                        }else{
                            Toast.makeText(
                                this@ThirdActivity,
                                "Por favor, habilite el permiso de solicitud.",
                                Toast.LENGTH_LONG
                            ).show()

                            val intentPermission = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            intentPermission.addCategory(Intent.CATEGORY_DEFAULT)
                            intentPermission.data = Uri.parse("package:$packageName")
                            intentPermission.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            intentPermission.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                            intentPermission.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                            startActivity(intentPermission)
                        }
                    }
                }else {
                    oldVersions(phoneCall)
                }
            }
        }
    }

    private fun oldVersions(phoneCall: String){
        val intentCall = Intent(Intent.ACTION_CALL, Uri.parse("tel:${phoneCall}"))
        startActivity(intentCall)
    }

    private fun checkPermission(permission: String): Boolean{
        val result = checkCallingOrSelfPermission(permission)
        return result == PackageManager.PERMISSION_GRANTED
    }
}