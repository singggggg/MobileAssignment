package com.example.mobileassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth

        val loginText: TextView = findViewById(R.id.loginRedirect)
        val registerBtn:Button = findViewById(R.id.register_btn)

        loginText.setOnClickListener {
            val intent = Intent(this,LoginPage::class.java)
            startActivity(intent)
        }

        registerBtn.setOnClickListener {
            performSignup()
        }
    }
    private fun performSignup(){
        val mEmail:EditText = findViewById(R.id.email)
        val mPassword:EditText = findViewById(R.id.password)

        if(mEmail.text.isEmpty() || mPassword.text.isEmpty()){
            Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT)
                .show()
        }

        val inputEmail = mEmail.text.toString()
        val inputPassword = mPassword.text.toString()

        auth.createUserWithEmailAndPassword(inputEmail, inputPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = Firebase.auth.currentUser

                    user!!.sendEmailVerification()
                        .addOnCompleteListener { task2 ->
                            if (task2.isSuccessful) {
                                // Sign in success, move next activity
                                val intent = Intent(this,LoginPage::class.java)
                                startActivity(intent)

                                Toast.makeText(baseContext, "Account created.",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }


                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener{
                Toast.makeText(this,"Error occured ${it.localizedMessage}",Toast.LENGTH_SHORT)
                    .show()
            }
    }
}