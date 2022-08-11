package com.example.lawyerapp.presentation.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.example.lawyerapp.R
import com.example.lawyerapp.databinding.ActivityMainBinding
import com.example.lawyerapp.databinding.ActivityOnboardingExample1Binding
import com.example.lawyerapp.presentation.adapter.OnboardingViewPagerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingExample1Activity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingExample1Binding
    private lateinit var mViewPager: ViewPager2
    private lateinit var textSkip: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_onboarding_example1)
        binding = ActivityOnboardingExample1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        (this as AppCompatActivity).supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.statusBarColor = this.resources.getColor(R.color.top_panel_welcome)
        }

        val pageIndicator = binding.pageIndicator
        mViewPager = binding.viewPager
        mViewPager.adapter = OnboardingViewPagerAdapter(this, this)
        TabLayoutMediator(pageIndicator, mViewPager) { _, _ -> }.attach()
        textSkip = binding.textSkip2
        textSkip.setOnClickListener {
            /*finish()
            val intent =
                Intent(applicationContext, OnboardingFinishActivity::class.java)
            startActivity(intent)
            Animatoo.animateSlideLeft(this)*/
        }

        val btnNextStep: FloatingActionButton = binding.btnNextStep

        btnNextStep.setOnClickListener {
            if (getItem() > mViewPager.childCount) {
                finish()
                /*val intent =
                    Intent(applicationContext, OnboardingFinishActivity::class.java)
                startActivity(intent)
                Animatoo.animateSlideLeft(this)*/
            } else {
                mViewPager.setCurrentItem(getItem() + 1, true)
            }
        }

    }

    private fun getItem(): Int {
        return mViewPager.currentItem
    }
}