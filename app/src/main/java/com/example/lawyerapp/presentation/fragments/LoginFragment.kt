package com.example.lawyerapp.presentation.fragments

import androidx.fragment.app.Fragment
import com.example.lawyerapp.databinding.FragmentSigninBinding
import com.example.lawyerapp.presentation.helpers.OnEditingFinishedListener

class LoginFragment: Fragment() {

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var _binding: FragmentSigninBinding? = null
    private val binding: FragmentSigninBinding
        get() = _binding ?: throw RuntimeException("FragmentSigninBinding == null")

}