package com.example.mobileassignment

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.recommendation_items.*

class UpdateRecommendationListActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<RecommendationAdmin>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_recommendation_list)

        userRecyclerview = findViewById(R.id.recommendationList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf<RecommendationAdmin>()
        getUserData()

    }

    private fun getUserData() {

        userRecyclerview.visibility = View.GONE

        dbref = FirebaseDatabase.getInstance().getReference("Recommendation")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){


                        val user = userSnapshot.getValue(RecommendationAdmin::class.java)
                        Log.d(ContentValues.TAG, "Value is: $user")
                        userArrayList.add(user!!)

                    }

                    userRecyclerview.adapter = RecAdapter(userArrayList)

                    val mAdapter = RecAdapter(userArrayList)
                    userRecyclerview.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : RecAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@UpdateRecommendationListActivity, UpdateRecommendationDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("recName", userArrayList[position].recName)
                            intent.putExtra("recDesc", userArrayList[position].recDesc)
                            intent.putExtra("uploadTime", userArrayList[position].uploadTime)
                            intent.putExtra("like", userArrayList[position].like)
                            intent.putExtra("dislike", userArrayList[position].dislike)
                            startActivity(intent)
                        }

                    })

                    userRecyclerview.visibility = View.VISIBLE

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


    }




    ///////////////////////////////////////////////////////

    private fun openUpdateDialog(
        //empId: String,
        recName: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_recommendation_dialog, null)

        mDialog.setView(mDialogView)

        val nameTitle = mDialogView.findViewById<EditText>(R.id.recNameET)
        val desc = mDialogView.findViewById<EditText>(R.id.recDescET)
        val uploadT = mDialogView.findViewById<EditText>(R.id.uploadTimeET)


        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        nameTitle.setText(intent.getStringExtra("recName").toString())
        desc.setText(intent.getStringExtra("recDesc").toString())
        uploadT.setText(intent.getStringExtra("uploadTime").toString())

        mDialog.setTitle("Updating $recName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateRecData(
                nameTitle.text.toString(),
                desc.text.toString(),
                uploadT.text.toString()
            )

            Toast.makeText(applicationContext, "Recommendation Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            etNameTitle.text = etNameTitle.text.toString()
            etDesc.text = etDesc.text.toString()
            tvTime.text = tvTime.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateRecData(
        name: String,
        desc: String,
        uploadT: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Recommendation")
        val recInfo = RecommendationAdmin(name, desc, uploadT)
        dbRef.setValue(recInfo)
    }

}