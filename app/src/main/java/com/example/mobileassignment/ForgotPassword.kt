package com.example.mobileassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val forgot_btn:Button = findViewById(R.id.forgot_button)
        val resetEmail:EditText = findViewById(R.id.resetEmail_ET)

        forgot_btn.setOnClickListener {
            val inputEmail = resetEmail.text.toString().trim()
            if(inputEmail.isEmpty()){
                Toast.makeText(this,"Please enter email address",Toast.LENGTH_SHORT).show()
            }else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(inputEmail)
                    .addOnCompleteListener { task->
                        if(task.isSuccessful){
                            Toast.makeText(this,"Reset link has been sent to your email",Toast.LENGTH_LONG).show()
                            finish()
                        }else{
                            Toast.makeText(this,task.exception!!.message.toString(),Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }
}