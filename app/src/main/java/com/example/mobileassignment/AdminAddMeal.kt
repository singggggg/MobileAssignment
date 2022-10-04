package com.example.mobileassignment

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import com.example.mobileassignment.AdminFragment.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database

class AdminAddMeal : AppCompatActivity() {

    val GALLERY_REQ_CODE = 1000
    lateinit var mealImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_add_meal)

        val nameET = findViewById<EditText>(R.id.name_EditText)
        val descET = findViewById<EditText>(R.id.desc_EditText)
        val priceET = findViewById<EditText>(R.id.price_EditText)
        val mealCat = resources.getStringArray(R.array.MealCat)
        mealImage = findViewById(R.id.MealImage)
        val mealGalBtn = findViewById<Button>(R.id.MealGallerybtn)
        val mealSubmitBtn = findViewById<Button>(R.id.MealSubmitBtn)
        val canBtn = findViewById<Button>(R.id.CancelBtn)
        val spinner = findViewById<Spinner>(R.id.Spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, mealCat
            )
            spinner.adapter = adapter
        }

        mealGalBtn.setOnClickListener {
            val iGallery = Intent(Intent.ACTION_PICK)
            iGallery.setType("image/*")
            iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(iGallery, GALLERY_REQ_CODE)

        }

        mealSubmitBtn.setOnClickListener {

            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Saving...")
            progressDialog.show()

            val name = nameET.text.toString().trim()
            val desc = descET.text.toString()
            val price = priceET.text.toString()
            val cat = spinner.selectedItem.toString()

            if (name.isEmpty()||desc.isEmpty()||price.isEmpty()) {
                nameET.error = "Please enter Meal Name"
                descET.error = "Please enter Description"
                priceET.error = "Please enter Price"
                if (progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(
                    applicationContext, "Error!!!!", Toast.LENGTH_LONG
                ).show()
            }else {
                val database = Firebase.database
                val myRef = database.getReference("MealMenu")

                val fb = MealMenu(name, desc, price,cat)

                myRef.child(name).setValue(fb).addOnCompleteListener {
                    Toast.makeText(
                        applicationContext, "Meal Menu saved successful", Toast.LENGTH_LONG
                    ).show()
                    nameET.setText("")
                    descET.setText("")
                    priceET.setText("")
                    if (progressDialog.isShowing) progressDialog.dismiss()

                }
            }

        }

        canBtn.setOnClickListener{
            this.finish()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE) {
                //For Gallery
                mealImage.setImageURI(data?.data)

            }
        }
    }
}

