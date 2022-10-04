package com.example.lawyerapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lawyerapp.R
import com.example.lawyerapp.databinding.*
import com.example.lawyerapp.presentation.helpers.OnEditingFinishedListener

class SafetySettingsUserAccountFragment: Fragment() {

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var _binding: FragmentSafetySettingsUserAccountBinding? = null
    private val binding: FragmentSafetySettingsUserAccountBinding
        get() = _binding ?: throw RuntimeException("FragmentAboutApplicationBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSafetySettingsUserAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //(activity as AppCompatActivity).supportActionBar?.title = "Войти"
        //(activity as AppCompatActivity).supportActionBar?.subtitle = "Зарегистрироваться"

        binding.cardProfileLogin.setOnClickListener {
            Toast.makeText(getActivity(), "this params click", Toast.LENGTH_SHORT).show()
        }

        binding.cardProfileActionsInTheSystem.setOnClickListener {
            Toast.makeText(getActivity(), "this params click", Toast.LENGTH_SHORT).show()
        }

    }

    fun launchFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_item_container, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }

}