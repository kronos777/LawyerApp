package com.example.lawyerapp.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lawyerapp.R
import com.example.lawyerapp.databinding.*
import com.example.lawyerapp.presentation.activity.MainActivity
import com.example.lawyerapp.presentation.helpers.OnEditingFinishedListener

class CreateSituationNewBuildingsOneFragment: Fragment() {

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var _binding: FragmentSituationNewBuildingsS1Binding? = null
    private val binding: FragmentSituationNewBuildingsS1Binding
        get() = _binding ?: throw RuntimeException("FragmentSituationAutoS1Binding == null")

    private var radioSelect: String = String()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSituationNewBuildingsS1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // binding.enterButton.background.alpha = 70
       // val textView = binding.textSignInCheck3
        val radioGroup = binding.radioGroupSituation

        radioGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = group.findViewById(checkedId)
                Toast.makeText(getActivity()," On checked change :"+
                        " ${radio.text}",
                    Toast.LENGTH_SHORT).show()
                radioSelect = radio.text.toString()
            })

/*
        binding.etMessageData.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("countInt", count.toString())
            }

        })*/

        binding.enterButton.setOnClickListener {
            launchFragment(CreateSituationNewBuildingsTwoFragment.newInstanceStr(radioSelect))
        }

        //(activity as AppCompatActivity).supportActionBar?.title = "Войти"
        //(activity as AppCompatActivity).supportActionBar?.subtitle = "Зарегистрироваться"


    }

    fun launchFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_item_container, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }


}