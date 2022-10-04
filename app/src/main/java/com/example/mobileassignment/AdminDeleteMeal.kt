package com.example.mobileassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mobileassignment.R
import com.example.mobileassignment.databinding.ActivityAdminDeleteMealBinding
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_admin_delete_meal.*

class AdminDeleteMeal : AppCompatActivity() {

    private lateinit var binding: ActivityAdminDeleteMealBinding
    private lateinit var database:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_delete_meal)
        binding = ActivityAdminDeleteMealBinding.inflate(layoutInflater)

        val nameET = findViewById<EditText>(R.id.meal_EditText)
        val mealDelBtn = findViewById<Button>(R.id.meal_deleteBtn)
        val bBtn = findViewById<Button>(R.id.b_button)


        meal_deleteBtn.setOnClickListener{

            var mealInput = nameET.text.toString()
            if(mealInput.isEmpty()){
                Toast.makeText(this,"Please enter a meal to be delete!!",Toast.LENGTH_SHORT).show()
            }else {
                deleteData(mealInput)
            }
        }

        bBtn.setOnClickListener{
            this.finish()
        }
    }

    private fun deleteData(mealInput:String){

        val dbref = FirebaseDatabase.getInstance().getReference("MealMenu")
        dbref.orderByChild("name").equalTo(mealInput).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var cutt = dbref.child(mealInput).removeValue()

                    cutt.addOnSuccessListener {
                        meal_EditText.text.clear()
                        Toast.makeText(this@AdminDeleteMeal, "Delete Successfull!!!", Toast.LENGTH_LONG).show()
                    }.addOnFailureListener {
                        Toast.makeText(this@AdminDeleteMeal, "Failed!Please Check Again!", Toast.LENGTH_LONG).show()

                    }
                }else {
                    Toast.makeText(
                        this@AdminDeleteMeal,
                        "Failed!Please Check Again!",
                        Toast.LENGTH_LONG
                    ).show()
                }
                }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}