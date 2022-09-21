package com.example.lawyerapp.presentation.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.marginLeft
import androidx.fragment.app.Fragment
import com.example.lawyerapp.databinding.*
import com.example.lawyerapp.presentation.helpers.OnEditingFinishedListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfileUserAccountFragment: Fragment() {

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var _binding: FragmentProfileUserAccountBinding? = null
    private val binding: FragmentProfileUserAccountBinding
        get() = _binding ?: throw RuntimeException("FragmentProfileUserAccountBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileUserAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //(activity as AppCompatActivity).supportActionBar?.title = "Войти"
        //(activity as AppCompatActivity).supportActionBar?.subtitle = "Зарегистрироваться"








        val textViewTitle = TextView(activity)

        with(textViewTitle) {
            textViewTitle.text = "Хотите выйти?"
            textViewTitle.textSize = 24.0F
            textViewTitle.setTypeface(null, Typeface.NORMAL)
            textViewTitle.gravity = Gravity.LEFT

        }


        binding.aboutApplicationHref2Delete.setOnClickListener {

            getActivity()?.let { it1 ->
                MaterialAlertDialogBuilder(it1)
                    .setTitle("Хотите выйти?")
                    //.setCustomTitle(textViewTitle)
                    .setMessage("Для повторной авторизации необходимо будет ввести логин и пароль")
                    .setNegativeButton("отмена") { dialog, which ->
                        // Respond to negative button press
                    }
                    .setPositiveButton("выйти") { dialog, which ->

                        // Respond to positive button press
                    }
                    .show()
            }
        }

    }



}