package com.example.mobileassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.mobileassignment.AdminFragment.AdminHomeFragment
import com.example.mobileassignment.AdminFragment.AdminMealMenuFragment
import com.example.mobileassignment.AdminFragment.AdminOrderFragment
import com.example.mobileassignment.AdminFragment.AdminRecommendationFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_admin_main.*
import kotlinx.android.synthetic.main.content_main_admin.*
import kotlinx.android.synthetic.main.content_main_admin.view.*
import java.lang.Exception


class AdminMainActivity : AppCompatActivity() {

    private val adminHomepageFragment = AdminHomeFragment()
    private val adminMenuFragment = AdminMealMenuFragment()
//    private val adminOrderHistory = AdminOrderFragment()
    private val adminRecommendationFragment = AdminRecommendationFragment()

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var user: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)
        setSupportActionBar(topAppBar)

        user = FirebaseAuth.getInstance()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout_admin)
        val navView: NavigationView = findViewById(R.id.nav_view_admin)
        val tab = findViewById<MaterialToolbar>(R.id.topAppBar)

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        btmNav_Admin.selectedItemId = R.id.ic_home

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        replaceFragment(adminHomepageFragment)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_home-> {
                    tab.title = "Home"
                    replaceFragment(adminHomepageFragment)
                }
                R.id.ic_menu-> {
                    tab.title = "Menu"
                    replaceFragment(adminMenuFragment)
                }
                R.id.ic_recommendationAdmin-> {
                    tab.title = "Recommendation"
                    replaceFragment(adminRecommendationFragment)
                }
              /*  R.id.menu_cart-> {
                    tab.title = "Cart"
                    replaceFragment(cartFragment)
                }*/
                R.id.logout -> signOut()
            }
            true
        }

        content_main_admin.btmNav_Admin.setOnItemSelectedListener {
            when(it.itemId){
                R.id.ic_home-> {
                    tab.title = "Home"
                    replaceFragment(adminHomepageFragment)
                }
                R.id.ic_menu-> {
                    tab.title = "Menu"
                    replaceFragment(adminMenuFragment)
                }
                R.id.ic_recommendationAdmin-> {
                    tab.title = "Recommendation";
                    replaceFragment(adminRecommendationFragment)
                }
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
    private fun signOut(){
        user.signOut()
        try{
            deleteFile("userdata")
        }catch (e: Exception){
            e.printStackTrace()
            false
        }
        val intent = Intent(this, LoginPage::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}