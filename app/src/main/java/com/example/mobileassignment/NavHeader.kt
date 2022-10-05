package com.example.mobileassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.StringBuilder

class NavHeader : AppCompatActivity() {

    private lateinit var user: FirebaseAuth
    private lateinit var dbref : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nav_header)
        user = FirebaseAuth.getInstance()

        val userName: TextView = findViewById(R.id.user_name)
        val userEmail: TextView = findViewById(R.id.user_email)

        val filename = "userdata"
        if(filename.trim()!=""){
            val fileInputStream: FileInputStream? = openFileInput(filename)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilderName = StringBuilder()
            val stringBuilderEmail = StringBuilder()
            var text:String?

            text = bufferedReader.readLine()
            stringBuilderName.append(text)

            text = bufferedReader.readLine()
            stringBuilderEmail.append(text)

            userName.setText(stringBuilderName.toString()).toString()
            userEmail.setText(stringBuilderEmail.toString()).toString()
        }
    }

}