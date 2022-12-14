package com.example.mobileassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.mobileassignment.Fragment.*
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main.view.*
import java.lang.Exception

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
        val tab = findViewById<MaterialToolbar>(R.id.topAppBar)

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        btmNav.selectedItemId = R.id.menu_homepage

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        replaceFragment(homepageFragment)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_recommendation-> {
                    tab.title = "Recommendation"
                    replaceFragment(recommendationFragment)
                }
                R.id.menu_mealPlan-> {
                    tab.title = "Meal Plan"
                    replaceFragment(mealPlanFragment)
                }
                R.id.menu_homepage-> {
                    tab.title = "Eat Heal"
                    replaceFragment(homepageFragment)
                }
/*                R.id.menu_menu-> {
                    tab.title = "Menu";
                    replaceFragment(menuFragment)
                }
                R.id.menu_cart-> {
                    tab.title = "Cart";
                    replaceFragment(cartFragment)
                }*/
//                R.id.menu_menu -> Toast.makeText(this,"Still under development",Toast.LENGTH_SHORT).show()
//                R.id.menu_cart -> Toast.makeText(this,"Still under development",Toast.LENGTH_SHORT).show()
                R.id.user_profile -> startActivity(Intent(this, UserProfile::class.java))
                R.id.logout -> signOut()
            }
            true
        }

        content_main.btmNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu_recommendation-> {
                    tab.title = "Recommendation"
                    replaceFragment(recommendationFragment)
                }
                R.id.menu_mealPlan-> {
                    tab.title = "Meal Plan"
                    replaceFragment(mealPlanFragment)
                }
                R.id.menu_homepage-> {
                    tab.title = "Eat Heal"
                    replaceFragment(homepageFragment)
                }
//                R.id.menu_menu-> replaceFragment(menuFragment)
//                R.id.menu_cart-> replaceFragment(cartFragment)
            }
            true
        }

    }

    private fun signOut(){
        user.signOut()
        try{
            deleteFile("userdata")
        }catch (e:Exception){
            e.printStackTrace()
            false
        }
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
}