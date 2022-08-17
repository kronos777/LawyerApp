package com.example.lawyerapp.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.lawyerapp.R
import com.example.lawyerapp.databinding.ActivityMainBinding
import com.example.lawyerapp.databinding.ActivityMainScreenBinding

class MainScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainScreenBinding
    //private lateinit var binding: ActivityMainScreenBinding
    lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (this as AppCompatActivity).supportActionBar?.title = "Судный день"


        val drawerLayout: DrawerLayout? = binding.drawerLayoutId

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


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


}