package com.example.mobileassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileassignment.R
import com.google.firebase.database.*

class AdminUpdateMeal : AppCompatActivity() {

    private lateinit var mealRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var mealList: ArrayList<MealMenu>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_update_meal)

        mealRecyclerView = findViewById(R.id.rvMeal)
        mealRecyclerView.layoutManager = LinearLayoutManager(this)
        mealRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        mealList = arrayListOf<MealMenu>()

        getMealData()

    }

    private fun getMealData() {
        mealRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("MealMenu")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mealList.clear()
                if (snapshot.exists()){
                    for (mealSnap in snapshot.children){
                        val mealData = mealSnap.getValue(MealMenu::class.java)
                        mealList.add(mealData!!)
                    }
                    val mAdapter = MealMenuAdapter(mealList)
                    mealRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : MealMenuAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@AdminUpdateMeal, MealDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("name", mealList[position].name)
                            intent.putExtra("description", mealList[position].description)
                            intent.putExtra("price", mealList[position].price)
                            intent.putExtra("category", mealList[position].category)
                            startActivity(intent)
                        }

                    })

                    mealRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}