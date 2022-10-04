package com.example.mobileassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mobileassignment.AdminFragment.*
import com.example.mobileassignment.Fragment.*
import kotlinx.android.synthetic.main.activity_admin_main.*
import kotlinx.android.synthetic.main.activity_main.*

class AdminMainActivity : AppCompatActivity() {
    private val adminHomepageFragment = AdminHomeFragment()
    private val adminMenuFragment = AdminMealMenuFragment()
    private val adminOrderHistory = AdminOrderFragment()
    private val adminRecommendationFragment = AdminRecommendationFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        btmNav_Admin.setOnItemSelectedListener {
            when(it.itemId){
                R.id.ic_home-> replaceFragment(adminHomepageFragment)
                R.id.ic_menu-> replaceFragment(adminMenuFragment)
                R.id.ic_order->replaceFragment(adminOrderHistory)
                R.id.ic_recommendationAdmin-> replaceFragment(adminRecommendationFragment)
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction =
                supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,fragment)
            transaction.commit()
        }
    }





}