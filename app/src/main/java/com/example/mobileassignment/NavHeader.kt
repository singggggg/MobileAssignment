package com.example.mobileassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class NavHeader : AppCompatActivity() {

    private lateinit var user: FirebaseAuth
    private lateinit var dbref : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nav_header)
        user = FirebaseAuth.getInstance()
        setProfile(user.currentUser)
    }

    private fun setProfile(currentUser: FirebaseUser?) {
        val userName: TextView = findViewById(R.id.user_name)
        val userEmail: TextView = findViewById(R.id.user_email)

        dbref = FirebaseDatabase.getInstance().getReference("User")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){
                        if (currentUser != null) {
                            if(userSnapshot.key.equals(currentUser.uid)){
                                userName.text = userSnapshot.child("firstname").value.toString().trim()
                                userEmail.text = userSnapshot.child("email").value.toString().trim()
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
}