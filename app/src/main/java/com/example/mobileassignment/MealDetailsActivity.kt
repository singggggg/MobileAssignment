package com.example.mobileassignment

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class MealDetailsActivity: AppCompatActivity() {
    private lateinit var tvName: TextView
    private lateinit var tvDesc: TextView
    private lateinit var tvPrice: TextView
    private lateinit var tvCategory: TextView
    private lateinit var btnUpdate: Button
//    private lateinit var btnDelete: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("name").toString(),
                intent.getStringExtra("description").toString(),
                intent.getStringExtra("price").toString(),
                intent.getStringExtra("category").toString()

            )

        }
    }

        private fun openUpdateDialog(
            name: String,
            description: String,
            price: String,
            category: String
        ) {
            val mDialog = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val mDialogView = inflater.inflate(R.layout.meal_update_dialog, null)

            mDialog.setView(mDialogView)

            val etName = mDialogView.findViewById<EditText>(R.id.etName)
            val etDescription = mDialogView.findViewById<EditText>(R.id.etDescription)
            val etPrice = mDialogView.findViewById<EditText>(R.id.etPrice)
            val etCategory = mDialogView.findViewById<EditText>(R.id.etCategory)

            val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

            etName.setText(intent.getStringExtra("name").toString())
            etDescription.setText(intent.getStringExtra("description").toString())
            etPrice.setText(intent.getStringExtra("price").toString())
            etCategory.setText(intent.getStringExtra("category").toString())

            mDialog.setTitle("Updating $name Record")
            etName.setTextIsSelectable(false)

            val alertDialog = mDialog.create()
            alertDialog.show()

            btnUpdateData.setOnClickListener {
                updateMealData(
                    etName.text.toString(),
                    etDescription.text.toString(),
                    etPrice.text.toString(),
                    etCategory.text.toString()
                )

                Toast.makeText(applicationContext, "Meal Data Updated", Toast.LENGTH_LONG).show()

                //we are setting updated data to our textviews
                tvName.text = etName.text.toString().trim()
                tvDesc.text = etDescription.text.toString()
                tvPrice.text = etPrice.text.toString().trim()
                tvCategory.text = etCategory.text.toString()

                alertDialog.dismiss()
            }

    }

        private fun updateMealData(
            name: String,
            description: String,
            price: String,
            category: String
        ) {
            val dbRef = FirebaseDatabase.getInstance().getReference("MealMenu").child(name)
            val mealInfo = MealMenu(name, description, price, category)
            dbRef.setValue(mealInfo)
        }

        private fun initView() {
            tvName = findViewById(R.id.tvName)
            tvDesc = findViewById(R.id.tvDesc)
            tvPrice = findViewById(R.id.tvPrice)
            tvCategory = findViewById(R.id.tvCategory)
            btnUpdate = findViewById(R.id.btnUpdate)
//        btnDelete = findViewById(R.id.btnDelete)
        }

        private fun setValuesToViews() {
            tvName.text = intent.getStringExtra("name")
            tvDesc.text = intent.getStringExtra("description")
            tvPrice.text = intent.getStringExtra("price")
            tvCategory.text = intent.getStringExtra("category")

        }


}

