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
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database

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
            val tab = findViewById<MaterialToolbar>(R.id.topAppBar)

            when(it.itemId){
                R.id.menu_recommendation-> {
                    tab.title = "Recommendation";
                    replaceFragment(recommendationFragment)
                }
                R.id.menu_mealPlan-> {
                    tab.title = "Meal Plan";
                    replaceFragment(mealPlanFragment)
                }
                R.id.menu_homepage-> {
                    tab.title = "Eat Heal";
                    replaceFragment(homepageFragment)
                }
                R.id.menu_menu-> {
                    tab.title = "Menu";
                    replaceFragment(menuFragment)
                }
                R.id.menu_cart-> {
                    tab.title = "Cart";
                    create()
                    /*replaceFragment(cartFragment)*/
                }
            }
            true
        }

        //set homepage as default
        btmNav.selectedItemId = R.id.menu_homepage

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

    fun replaceFrag(fragment: Fragment){
        replaceFragment(fragment)
    }

    var i:Int = 1

    fun create(){
        val db = Firebase.database
        val myRef = db.getReference("MealPlan")

        val mp = MealPlan("EpalpalMeal","280","0","28-9-2022")

        myRef.child("EpalpalMeal"+i).setValue(mp).addOnCompleteListener {
            Toast.makeText(applicationContext, "Mp saved successful", Toast.LENGTH_LONG).show()
        }
        i++
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