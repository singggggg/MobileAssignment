package com.example.mobileassignment

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.*
import java.lang.Exception
import java.lang.StringBuilder

class EditUser : AppCompatActivity() {

    private lateinit var user: FirebaseAuth
    private lateinit var dbref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user)

        val firstname_ET:EditText = findViewById(R.id.userFirstNameET)
        val lastname_ET:EditText = findViewById(R.id.userLastNameET)
        val email_TV:TextView = findViewById(R.id.userEmailTV)
        val returnBtn: ImageView = findViewById(R.id.return_btn)
        val updateBtn: Button = findViewById(R.id.update_btn)

        val filename = "userdata"
        if(filename.trim()!=""){
            val fileInputStream: FileInputStream? = openFileInput(filename)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder = StringBuilder()
            var text:String?

            text = bufferedReader.readLine()
            stringBuilder.append(text)
            firstname_ET.setText(stringBuilder.toString()).toString()
            stringBuilder.clear()

            text = bufferedReader.readLine()
            stringBuilder.append(text)
            lastname_ET.setText(stringBuilder.toString()).toString()
            stringBuilder.clear()

            text = bufferedReader.readLine()
            stringBuilder.append(text)
            email_TV.setText(stringBuilder.toString()).toString()
            stringBuilder.clear()
        }

        updateBtn.setOnClickListener {
            val file: String = "userdata"
            var data: String?
            var fileOutputStream: FileOutputStream
            user = FirebaseAuth.getInstance()
            dbref = FirebaseDatabase.getInstance().getReference("User").child(user.uid.toString())

            if (email_TV.text.isEmpty() || firstname_ET.text.isEmpty() || lastname_ET.text.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val userData = User(
                    firstname_ET.text.toString().trim(),
                    lastname_ET.text.toString().trim(),
                    email_TV.text.toString().trim()
                )
                try {
                    dbref.setValue(userData)
                    data = firstname_ET.text.toString().trim() + "\n" + lastname_ET.text.toString()
                        .trim() +
                            "\n" + email_TV.text.toString().trim()
                    try {
                        fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)
                        fileOutputStream.write(data!!.toByteArray())
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    } catch (e: NumberFormatException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        with(e) { printStackTrace() }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    Toast.makeText(this, "Profile updated.", Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error occurred. ${e.localizedMessage}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }


        returnBtn.setOnClickListener {
            this.finish()
        }

    }
}