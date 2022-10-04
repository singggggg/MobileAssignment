package com.example.mobileassignment


import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileassignment.R
import com.google.firebase.database.*


class AdminViewMeal : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview: RecyclerView
    private lateinit var userArrayList: ArrayList<MealMenu>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_view_meal)

        val bBtn = findViewById<Button>(R.id.b_button)

        userRecyclerview = findViewById(R.id.MealMenuList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf<MealMenu>()
        getUserData()

        bBtn.setOnClickListener{
            this.finish()
        }

    }

    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("MealMenu")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){


                        val user = userSnapshot.getValue(MealMenu::class.java)
                        Log.d(ContentValues.TAG, "Value is: $user")
                        userArrayList.add(user!!)

                    }

                    userRecyclerview.adapter = MealAdapter(userArrayList)


                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


    }

}