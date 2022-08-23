package com.example.lawyerapp.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import com.example.lawyerapp.R
import com.example.lawyerapp.databinding.ActivityTestToolbarBinding
import com.example.lawyerapp.presentation.helpers.BottomFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomsheet.BottomSheetBehavior

class TestToolbarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestToolbarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTestToolbarBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_test_toolbar)


        binding.arrowBottomSheetShow.setOnClickListener {
            BottomFragment().show(supportFragmentManager, "tag")
        }
        //BottomFragment().show(supportFragmentManager, "tag")

    }


}