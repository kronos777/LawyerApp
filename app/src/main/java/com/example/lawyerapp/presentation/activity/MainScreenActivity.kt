package com.example.lawyerapp.presentation.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.lawyerapp.R
import com.example.lawyerapp.databinding.ActivityMainScreenBinding
import com.example.lawyerapp.presentation.fragments.AboutAppFragment
import com.example.lawyerapp.presentation.fragments.AskLawyerQuestionFragment
import com.example.lawyerapp.presentation.fragments.SettingsUserAccountFragment
import com.example.lawyerapp.presentation.fragments.UserSettingsFragment
import com.google.android.material.appbar.MaterialToolbar
import java.lang.String
import kotlin.Boolean
import kotlin.apply


class MainScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainScreenBinding
    //private lateinit var binding: ActivityMainScreenBinding
    lateinit var toggle: ActionBarDrawerToggle

    private var redCircle: FrameLayout? = null
    private var countTextView: TextView? = null
    private var alertCount = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // (this as AppCompatActivity).supportActionBar?.setCustomView(binding.appBarTop)
        //(this as AppCompatActivity).supportActionBar?.title = "Судный день"
        binding.toolBar.title = "О приложении"
        //(this as MainScreenActivity).enableHomeBackIcon(false)
        /*test*/
       /* */
       // val navController = binding.appBarTop
        val drawerLayout: DrawerLayout? = binding.drawerLayoutId

       // setSupportActionBar(binding.toolBar)


      //  setupActionBarWithNavController(navController, appBarConfiguration)
        redCircle = findViewById(R.id.view_alert_red_circle)
        countTextView = findViewById(R.id.view_alert_count_textview)

        // create instance of the material toolbar
        val materialToolbar: MaterialToolbar = binding.toolBar
            //  setSupportActionBar(materialToolbar)
        // assign the on menu item click listener
        //materialToolbar.setOnMenuItemClickListener {
        val itemM = findViewById<View>(R.id.message)
        itemM.setOnClickListener {
            Toast.makeText(this, "Favorites Clicked", Toast.LENGTH_SHORT).show()
            clearAlertIcon()
        }
        materialToolbar.setOnMenuItemClickListener {
           // Toast.makeText(this, "Favorites Clsadsaicked"+it.itemId, Toast.LENGTH_SHORT).show()
            when (it.itemId) {
                R.id.message -> {
                    Toast.makeText(this, "Favorites Clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.notifications -> {
                    Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show()
                    alertCount = (alertCount + 1) % 11; // cycle through 0 - 10
                    updateAlertIcon()
                    true
                }
                else -> false
            }
        }




        toggle = getActionBarDrawerToggle(binding.drawerLayoutId, binding.toolBar).apply {
            setToolbarNavigationClickListener {
                // Back to home fragment for any hit to the back button
                //   navController.navigate(R.id.app_bar_top)
            }
            // Intialize the icon at the app start
            enableHomeBackIcon(false)
        }
        /*test*/


     /*   val drawerLayout: DrawerLayout? = binding.drawerLayoutId

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
*/

        binding.navView.setNavigationItemSelectedListener {

            when (it.itemId) {
                //    R.id.muItem1 -> goGroupFragment()
                //      R.id.muItem2 -> launchFragment(SettingsItemFragment())
                R.id.muItem2 -> Toast.makeText(this, "item 1", Toast.LENGTH_SHORT).show()
                R.id.muItem3 -> Toast.makeText(this, "item 2", Toast.LENGTH_SHORT).show()
                R.id.muItem4 -> Toast.makeText(this, "item 3", Toast.LENGTH_SHORT).show()
                R.id.muItem5 -> Toast.makeText(this, "item 4", Toast.LENGTH_SHORT).show()
                R.id.muItem6 -> Toast.makeText(this, "item 5", Toast.LENGTH_SHORT).show()
                R.id.muItem7 -> Toast.makeText(this, "item 6", Toast.LENGTH_SHORT).show()


            }
            true
        }

/*
        binding.bottomSheetShowBar.setOnClickListener {
            BottomFragment().show(supportFragmentManager, "tag")
            Toast.makeText(this, "Bottom show", Toast.LENGTH_SHORT).show()
        }*/


        launchFragment(SettingsUserAccountFragment())
       // launchFragment(AboutAppFragment())
        //launchFragment(UserSettingsFragment())
        //launchFragment(AskLawyerQuestionFragment())
        //launchFragment(MainScreenFragment())

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun enableHomeBackIcon(enabled: Boolean) {
        // Enable/Disable opening the drawer from the start side
        toggle?.isDrawerIndicatorEnabled = !enabled

        // Change the default burger icon
        supportActionBar?.setHomeAsUpIndicator(
            if (enabled) R.drawable.next
            else R.drawable.ic_baseline_menu_24
        )
    }

    private fun getActionBarDrawerToggle(
        drawerLayout: DrawerLayout,
        toolbar: Toolbar
    ): ActionBarDrawerToggle {
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open,
            R.string.close
        )
        drawerLayout.addDrawerListener(toggle!!)
        toggle?.syncState()
        return toggle as ActionBarDrawerToggle
    }


    private fun launchFragment(fragment: Fragment) {
     //   this.supportFragmentManager.popBackStack()
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun updateAlertIcon() {
        // if alert count extends into two digits, just show the red circle
        if (0 < alertCount && alertCount < 10) {
            countTextView?.setText(String.valueOf(alertCount))
        } else {
            countTextView?.setText("")
        }
        redCircle?.setVisibility(if (alertCount > 0) VISIBLE else GONE)
    }

    private fun clearAlertIcon() {
        // if alert count extends into two digits, just show the red circle
        alertCount = 0

        redCircle?.setVisibility(GONE)
    }



}

