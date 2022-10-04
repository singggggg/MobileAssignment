package com.example.mobileassignment

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import com.example.mobileassignment.Fragment.*
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main.view.*
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

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var user:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(topAppBar)

        user = FirebaseAuth.getInstance()

        val drawerLayout:DrawerLayout = findViewById(R.id.drawerLayout)
        val navView:NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_homepage -> Toast.makeText(applicationContext,"Clicked home",Toast.LENGTH_SHORT).show()
                R.id.menu_recommendation -> Toast.makeText(applicationContext,"Clicked recommendation",Toast.LENGTH_SHORT).show()
                R.id.menu_mealPlan -> Toast.makeText(applicationContext,"Clicked meal plan",Toast.LENGTH_SHORT).show()
                R.id.menu_menu -> Toast.makeText(applicationContext,"Clicked meal menu",Toast.LENGTH_SHORT).show()
                R.id.menu_cart -> Toast.makeText(applicationContext,"Clicked cart",Toast.LENGTH_SHORT).show()
                R.id.user_profile -> Toast.makeText(applicationContext,"Clicked user profile",Toast.LENGTH_SHORT).show()
                R.id.logout -> signOut()
            }
            true
        }

        content_main.btmNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu_recommendation-> replaceFragment(recommendationFragment)
                R.id.menu_mealPlan-> replaceFragment(mealPlanFragment)
                R.id.menu_homepage-> replaceFragment(homepageFragment)
                R.id.menu_menu-> replaceFragment(menuFragment)
                R.id.menu_cart-> replaceFragment(cartFragment)
            }
            true
        }

    }

    private fun signOut(){
        user.signOut()
        val intent = Intent(this, LoginPage::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction =
                supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,fragment)
            transaction.commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
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