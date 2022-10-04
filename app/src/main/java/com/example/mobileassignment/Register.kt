package com.example.mobileassignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database:DatabaseReference

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
    private fun performSignup() {
        val email: EditText = findViewById(R.id.email)
        val password: EditText = findViewById(R.id.password)
        val firstName: EditText = findViewById(R.id.firstName_ET)
        val lastName: EditText = findViewById(R.id.lastName_ET)

        if (email.text.isEmpty() || password.text.isEmpty() || firstName.text.isEmpty() || lastName.text.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT)
                .show()
        } else {

            val inputEmail = ""+email.text.toString().trim()
            val inputPassword = password.text.toString()
            val inputFirstName = firstName.text.toString().trim()
            val inputLastName = lastName.text.toString().trim()

            auth.createUserWithEmailAndPassword(inputEmail, inputPassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = Firebase.auth.currentUser

                        user!!.sendEmailVerification()
                            .addOnCompleteListener { task1 ->
                                if (task1.isSuccessful) {

                                    addUserdata(user.uid, inputFirstName, inputLastName,inputEmail)

                                    auth.signOut()
                                    // Register success, move next activity
                                    val intent = Intent(this, LoginPage::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)

                                    Toast.makeText(
                                        baseContext, "Account created.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error Occurred ${it.localizedMessage}", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }

    private fun addUserdata(userID:String,inputFirstName:String,inputLastName:String,inputEmail:String){
        database = Firebase.database.reference

        val userData = User(inputFirstName,inputLastName,inputEmail)

        database.child("User").child(userID).setValue(userData)
        /*
        encodeUserEmail(inputEmail)?.let {
            database.child("User").child(it)
                .setValue(userData)
        }*/
    }
   /* fun encodeUserEmail(userEmail: String): String? {
        return userEmail.replace(".", ",")
    }

    fun decodeUserEmail(userEmail: String): String? {
        return userEmail.replace(",", ".")
    }*/
}