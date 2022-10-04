package com.example.mobileassignment

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import com.example.mobileassignment.Fragment.*
import kotlinx.android.synthetic.main.activity_main.*
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    private val recommendationFragment = RecommendationFragment()
    private val mealPlanFragment = MealPlanFragment()
    private val homepageFragment = HomePageFragment()
    private val menuFragment = MenuFragment()
    private val cartFragment = CartFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btmNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu_recommendation-> replaceFragment(recommendationFragment)
                R.id.menu_mealPlan-> replaceFragment(mealPlanFragment)
                R.id.menu_homepage-> replaceFragment(homepageFragment)
                R.id.menu_menu-> replaceFragment(menuFragment)
                R.id.menu_cart-> replaceFragment(cartFragment)
            }
            true
        }

        val chgRoleBtn = findViewById<Button>(R.id.changeRoleBtn)
        chgRoleBtn.setOnClickListener{
            val intent = Intent(this,AdminMainActivity::class.java)
            startActivity(intent)
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

/*    private fun getCurrentMillisDateTime(): Long?{
        val currentMillis = System.currentTimeMillis()
        return currentMillis + 28800000
    }

    private fun getDateTime(s: String): String? {
        try {
            val sdf = SimpleDateFormat("MM/dd/yyyy hh:mm:ss")
            val netDate = Date(s.toLong())
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }*/
}