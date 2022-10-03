package com.example.mobileassignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginPage : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        auth = Firebase.auth

        val loginText:TextView = findViewById(R.id.registerRedirect)
        val loginBtn: Button = findViewById(R.id.login_btn)
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            // User is signed in
            if(user.isEmailVerified){
                // Sign in success, redirect to main activity
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(baseContext, "Logged in.", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Please verify your email address.",Toast.LENGTH_SHORT).show()
            }
        } else {
            // User is signed out

        }
        loginText.setOnClickListener {
            val intent = Intent(this,Register::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener {
            performLogin()
        }
    }

    private fun performLogin() {
        val email: EditText = findViewById(R.id.email)
        val password: EditText = findViewById(R.id.password)

        if (email.text.isEmpty() || password.text.isEmpty()) {
            Toast.makeText(this, "Please enter all fields.", Toast.LENGTH_SHORT).show()
        } else {

            val emailInput = email.text.toString()
            val passwordInput = password.text.toString()

            auth.signInWithEmailAndPassword(emailInput, passwordInput)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        updateUI(auth.currentUser)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(
                        baseContext, "Authentication failed. ${it.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

        }
    }
    private fun updateUI(currentUser:FirebaseUser?){
        if (currentUser != null) {
            if(currentUser.isEmailVerified){
                // Sign in success, redirect to main activity
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(baseContext, "Logged in.", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Please verify your email address.",Toast.LENGTH_SHORT).show()
            }
        }
    }
}