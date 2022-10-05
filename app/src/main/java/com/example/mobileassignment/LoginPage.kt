package com.example.mobileassignment

import android.app.role.RoleManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class LoginPage : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var dbref : DatabaseReference
    private lateinit var user:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        user = FirebaseAuth.getInstance()

        auth = Firebase.auth

        val loginText:TextView = findViewById(R.id.registerRedirect)
        val loginBtn: Button = findViewById(R.id.login_btn)
        val user = FirebaseAuth.getInstance().currentUser
        val forgotPassword_btn:TextView = findViewById(R.id.forgotPW_TV)

        val role = resources.getStringArray(R.array.role)
        val roleSpinner: Spinner = findViewById(R.id.roleSpinner)

        if (user != null) {
            // User is signed in
            if(user.isEmailVerified){
                // Sign in success, redirect to main activity
                val intent = Intent(this,MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }else{
                Toast.makeText(this,"Please verify your email address.",Toast.LENGTH_SHORT).show()
            }
        }

        if(roleSpinner!=null){
            val adapter = ArrayAdapter(this,R.layout.spinner_list,role)
            roleSpinner.adapter = adapter
        }

        loginText.setOnClickListener {
            val intent = Intent(this,Register::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener {
            performLogin()
        }

        forgotPassword_btn.setOnClickListener {
            val intent = Intent(this,ForgotPassword::class.java)
            startActivity(intent)
        }
    }

    private fun performLogin() {
        val email: EditText = findViewById(R.id.email)
        val password: EditText = findViewById(R.id.password)
        val roleSpinner: Spinner = findViewById(R.id.roleSpinner)

        if (email.text.isEmpty() || password.text.isEmpty()) {
            Toast.makeText(this, "Please enter all fields.", Toast.LENGTH_SHORT).show()
        } else {

            val emailInput = email.text.toString().trim()
            val passwordInput = password.text.toString()
            val role =roleSpinner.selectedItem.toString().trim()

            auth.signInWithEmailAndPassword(emailInput, passwordInput)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        updateUI(auth.currentUser,role)
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
    private fun updateUI(currentUser:FirebaseUser?,role:String){
        if (currentUser != null) {
            if(role=="Admin"){
                getAdminData(currentUser)
            }else {
                if (currentUser.isEmailVerified) {
                    // Sign in success, redirect to main activity
                    getUserData(currentUser)
                } else {
                    Toast.makeText(this, "Please verify your email address.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun getAdminData(currentUser: FirebaseUser?) {

        dbref = FirebaseDatabase.getInstance().getReference("Admin")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){
                        if (currentUser != null) {
                            if(userSnapshot.key.equals(currentUser.uid)){
                                val intent = Intent(this@LoginPage, AdminMainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            }
                        }
                    }
                    user.signOut()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                user.signOut()
            }
        })
    }

    private fun getUserData(currentUser: FirebaseUser?) {

        val file:String = "userdata"
        var data:String?
        var fileOutputStream:FileOutputStream

        dbref = FirebaseDatabase.getInstance().getReference("User")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){
                        if (currentUser != null) {
                            if(userSnapshot.key.equals(currentUser.uid)){

                                data = userSnapshot.child("firstname").value.toString().trim() + "\n" +userSnapshot.child("lastname").value.toString().trim()+
                                        "\n"+ userSnapshot.child("email").value.toString().trim()
                                try{
                                    fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)
                                    fileOutputStream.write(data!!.toByteArray())
                                }catch(e: FileNotFoundException){
                                    e.printStackTrace()
                                }catch(e:NumberFormatException){
                                    e.printStackTrace()
                                }catch(e: IOException){
                                    with(e){printStackTrace()}
                                }catch(e:Exception){
                                    e.printStackTrace()
                                }

                                val intent = Intent(this@LoginPage, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                Toast.makeText(baseContext, "Logged in.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                user.signOut()
            }
        })
    }
}