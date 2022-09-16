package com.example.lawyerapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lawyerapp.databinding.*
import com.example.lawyerapp.presentation.helpers.OnEditingFinishedListener

class ChangeUserPhoneConfirmationFragment: Fragment() {

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var _binding: FragmentChangeUserPhoneConfirmationBinding? = null
    private val binding: FragmentChangeUserPhoneConfirmationBinding
        get() = _binding ?: throw RuntimeException("FragmentChangeUserPhoneConfirmationBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangeUserPhoneConfirmationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //(activity as AppCompatActivity).supportActionBar?.title = "Войти"
        //(activity as AppCompatActivity).supportActionBar?.subtitle = "Зарегистрироваться"


    }



}