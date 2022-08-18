package com.example.lawyerapp.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.lawyerapp.R
import com.example.lawyerapp.databinding.ActivityMainBinding
import com.example.lawyerapp.databinding.ActivityMainScreenBinding
import com.google.android.material.appbar.MaterialToolbar

class MainScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainScreenBinding
    //private lateinit var binding: ActivityMainScreenBinding
    lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // (this as AppCompatActivity).supportActionBar?.setCustomView(binding.appBarTop)
        //(this as AppCompatActivity).supportActionBar?.title = "Судный день"

        //(this as MainScreenActivity).enableHomeBackIcon(false)
        /*test*/
       /* */
       // val navController = binding.appBarTop
        val drawerLayout: DrawerLayout? = binding.drawerLayoutId

       // setSupportActionBar(binding.toolBar)


      //  setupActionBarWithNavController(navController, appBarConfiguration)


        // create instance of the material toolbar
        val materialToolbar: MaterialToolbar = binding.toolBar
            //  setSupportActionBar(materialToolbar)
        // assign the on menu item click listener
        materialToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.favorite -> {
                    Toast.makeText(this, "Favorites Clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.search -> {
                    Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show()
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

}