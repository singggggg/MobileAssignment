package com.example.mobileassignment.Admin

import android.app.ActionBar
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mobileassignment.R
import com.example.mobileassignment.RecommendationAdmin
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class AddRecommentdationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recommentdation)

        var actionBar = getSupportActionBar()
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }


        val recomName : EditText = findViewById(R.id.recNameET)
        val recomDesc : EditText = findViewById(R.id.recDescET)
        val uploadTime : EditText = findViewById(R.id.uploadTimeET)
        val submitBtn : Button = findViewById(R.id.submitRecBtn)

        //date Picker
        uploadTime.setOnClickListener {

            // on below line we are getting
            // the instance of our calendar.
            val c = Calendar.getInstance()

            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // on below line we are creating a
            // variable for date picker dialog.
            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our edit text.
                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    uploadTime.setText(dat)
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            // at last we are calling show
            // to display our date picker dialog.
            datePickerDialog.show()
        }



        submitBtn.setOnClickListener {


            val titleName = recomName.text.toString().trim()
            val reDesc = recomDesc.text.toString()
            val uploadT = uploadTime.text.toString().trim()


            if (titleName.isEmpty()) {
                recomName.error = "Please enter the title name"

            }
            val database = Firebase.database
            val myRef = database.getReference("Recommendation")

            val fb = RecommendationAdmin(titleName, reDesc, uploadT)

            myRef.child(titleName).setValue(fb).addOnCompleteListener {
                Toast.makeText(applicationContext, "Recommendation Upload Successful", Toast.LENGTH_LONG)
                    .show()
            }
        }

        val backBtn : Button = findViewById(R.id.back_button)

        backBtn.setOnClickListener {
            this.finish()
        }


    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

}