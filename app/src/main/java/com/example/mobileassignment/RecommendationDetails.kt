package com.example.mobileassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class RecommendationDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommendation_details)

        val viewName = findViewById<TextView>(R.id.tv_recDetail_Name)
        val viewDate = findViewById<TextView>(R.id.tv_recDetails_uploadDate)
        val viewContent = findViewById<TextView>(R.id.tv_recDetails_content)
        val viewPic = findViewById<ImageView>(R.id.iv_recDetails_image)

        viewName.text = intent.getStringExtra("name")
        viewDate.text = intent.getStringExtra("uploadTime")
        viewContent.text = intent.getStringExtra("desc")
        //viewPic.drawable = intent.getStringExtra("name") //TODO: unsolvable image part


    }
}