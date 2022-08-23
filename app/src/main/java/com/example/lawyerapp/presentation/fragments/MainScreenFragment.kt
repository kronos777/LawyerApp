package com.example.lawyerapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lawyerapp.databinding.FragmentLoginBinding
import com.example.lawyerapp.databinding.FragmentMainScreenBinding
import com.example.lawyerapp.databinding.FragmentSigninBinding
import com.example.lawyerapp.presentation.helpers.OnEditingFinishedListener

class MainScreenFragment: Fragment() {

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var _binding: FragmentMainScreenBinding? = null
    private val binding: FragmentMainScreenBinding
        get() = _binding ?: throw RuntimeException("FragmentMainScreenBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // (activity as AppCompatActivity).supportActionBar?.title = "Войти"
        //(activity as AppCompatActivity).supportActionBar?.subtitle = "Зарегистрироваться"


    }



}