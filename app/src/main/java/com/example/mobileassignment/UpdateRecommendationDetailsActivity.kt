package com.example.mobileassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.recommendation_admin_items.*

class UpdateRecommendationDetailsActivity : AppCompatActivity() {

    private lateinit var tvNameTitle: TextView
    private lateinit var tvDesc: TextView
    private lateinit var tvTime: TextView
    private lateinit var likeTV: TextView
    private lateinit var dislikeTV: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_recommendation_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("recName").toString(),
                intent.getStringExtra("recDesc").toString(),
                intent.getStringExtra("uploadTime").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("recName").toString()
            )
        }

        val backBtn : Button = findViewById(R.id.back_button)

        backBtn.setOnClickListener {
            this.finish()
        }

    }

    private fun initView() {
        tvNameTitle = findViewById(R.id.etNameTitle)
        tvDesc = findViewById(R.id.etDesc)
        tvTime = findViewById(R.id.tvTime)
        likeTV = findViewById(R.id.likeTV)
        dislikeTV = findViewById(R.id.dislikeTV)


        btnUpdate = findViewById(R.id.btnUpdateData)
        btnDelete = findViewById(R.id.btnDeleteData)
    }

    private fun setValuesToViews() {
        tvNameTitle.text = intent.getStringExtra("recName")
        tvDesc.text = intent.getStringExtra("recDesc")
        tvTime.text = intent.getStringExtra("uploadTime")
        //likeTV.text = intent.getStringExtra("like").toString()
        //dislikeTV.text = intent.getStringExtra("dislike").toString()

    }

    private fun openUpdateDialog(
        recName: String,
        recDesc: String,
        uploadTime: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_recommendation_dialog, null)

        mDialog.setView(mDialogView)

        val etRecNameTitle = mDialogView.findViewById<EditText>(R.id.etNameTitle)
        val etDesc = mDialogView.findViewById<EditText>(R.id.etDesc)
        val etTime = mDialogView.findViewById<EditText>(R.id.etTime)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etRecNameTitle.setText(intent.getStringExtra("recName").toString())
        etDesc.setText(intent.getStringExtra("recDesc").toString())
        etTime.setText(intent.getStringExtra("uploadTime").toString())

        mDialog.setTitle("Updating $recName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateRecData(
                recName,
                etDesc.text.toString(),
                etTime.text.toString()
            )

            Toast.makeText(applicationContext, "Recommendation Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvNameTitle.text = etRecNameTitle.text.toString()
            tvDesc.text = etDesc.text.toString()
            tvTime.text = etTime.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateRecData(
        recName: String,
        recDesc: String,
        uploadTime: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Recommendation").child(recName)
        val recInfo = RecommendationAdmin( recName, recDesc , uploadTime)
        dbRef.setValue(recInfo)
    }

    private fun deleteRecord(
        recName: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Recommendation").child(recName)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Recommendation data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this@UpdateRecommendationDetailsActivity, UpdateRecommendationListActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }


}