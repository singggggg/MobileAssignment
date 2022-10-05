package com.example.mobileassignment

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.StringBuilder

class UserProfile : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        user = FirebaseAuth.getInstance()

        auth = Firebase.auth

        val userName_TV:TextView = findViewById(R.id.userProf_Name)
        val userEmail_TV:TextView = findViewById(R.id.userProf_Email)
        val returnBtn:ImageView = findViewById(R.id.return_btn)
        val editBtn:Button = findViewById(R.id.editUser_btn)

        val filename = "userdata"
        if(filename.trim()!=""){
            val fileInputStream: FileInputStream? = openFileInput(filename)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilderName = StringBuilder()
            val stringBuilderEmail = StringBuilder()
            var text:String?

            text = bufferedReader.readLine()
            stringBuilderName.append("$text ")
            text = bufferedReader.readLine()
            stringBuilderName.append(text)

            text = bufferedReader.readLine()
            stringBuilderEmail.append(text)

            userName_TV.setText(stringBuilderName.toString()).toString()
            userEmail_TV.setText(stringBuilderEmail.toString()).toString()
        }

        editBtn.setOnClickListener {
            val intent = Intent(this, EditUser::class.java)
            startActivity(intent)
        }

        returnBtn.setOnClickListener {
            this.finish()
        }
    }
}