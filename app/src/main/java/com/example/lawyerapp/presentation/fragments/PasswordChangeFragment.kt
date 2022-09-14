package com.example.lawyerapp.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lawyerapp.databinding.FragmentPasswordChangeBinding
import com.example.lawyerapp.presentation.adapter.ListChangePasswordRulesAdapter
import com.example.lawyerapp.presentation.helpers.DataChangePasswordRulesModel
import com.example.lawyerapp.presentation.helpers.OnEditingFinishedListener
import com.example.lawyerapp.presentation.helpers.ValidPassword
import com.example.lawyerapp.presentation.viewmodel.UserViewModel
import com.google.android.material.textfield.TextInputLayout

class PasswordChangeFragment() : Fragment() {

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener
    private lateinit var listView: ListView
    private lateinit var textInputLayoutNewPassword: TextInputLayout
    private lateinit var textInputLayoutNewPasswordTwice: TextInputLayout
    private val EMPTY_STRING = ""



    private var _binding: FragmentPasswordChangeBinding? = null
    private val binding: FragmentPasswordChangeBinding
        get() = _binding ?: throw RuntimeException("FragmentPasswordRecoveryBinding == null")


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordChangeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        (activity as AppCompatActivity).supportActionBar?.title = "Изменение пороля"

        listView = binding.textChangePasswordTxt
        textInputLayoutNewPassword = binding.tilOldPasswordTwice
        textInputLayoutNewPasswordTwice = binding.tilNewPassword



        val rulesList = ArrayList<DataChangePasswordRulesModel>()
            rulesList.add(DataChangePasswordRulesModel("как минимум одна цифра"))
            rulesList.add(DataChangePasswordRulesModel("как минимум одна заглавная и одна строчная буква"))
            rulesList.add(DataChangePasswordRulesModel("не менее 8 символов"))
            rulesList.add(DataChangePasswordRulesModel("без пробелов"))
            rulesList.add(DataChangePasswordRulesModel("только латинские буквы"))


        val adapterChangePasswordRulesModel = ListChangePasswordRulesAdapter(rulesList!!, requireContext().applicationContext)
        listView.adapter = adapterChangePasswordRulesModel





        binding.changePasswordButton.setOnClickListener {
          /*  var testStr = "Qqqqqqqqq    q!1"
            //testStr = testStr.replace("\\s".toRegex(), "")

            for (index in testStr.indices) {
                if(testStr[index].toString() == " ") {
                    Log.d("strChar", testStr[index].toString())
                }

            }
*/

          //  Toast.makeText(getActivity(), "ura :" + testStr, Toast.LENGTH_SHORT).show()
            val oldPassword: String = binding.etOldPassword.text.toString()
            val newPassword = binding.etOldPasswordTwice.text.toString()
            val newPasswordRepeat: String = binding.etNewPassword.text.toString()
           // hideError()

            val validPasswd = ValidPassword().checkPassword("qqqqqqqqq", "Qqqqqqqq!1", "Qqqqqqqq!1")
            // val validPasswd = ValidPassword().checkPassword(oldPassword, newPassword, newPasswordRepeat)


                if(validPasswd == "ok") {
                    UserViewModel().changePassword("Qqqqqqqq!1")
                    Toast.makeText(getActivity(), "ura", Toast.LENGTH_SHORT).show()
                    hideError()
                } else {
                    showError(validPasswd)
                    Toast.makeText(getActivity(), "false", Toast.LENGTH_SHORT).show()
                }
            /*if(ValidPassword().checkPasswd("U1vviytvty!", "U1vviytvty!") == true) {
                Toast.makeText(getActivity(), "ura", Toast.LENGTH_SHORT).show()
                hideError()
            } else {
                showError("asdsadadd")
                Toast.makeText(getActivity(), "false", Toast.LENGTH_SHORT).show()

            }*/

        }



    }
    private fun showError(stringError: String) {
        textInputLayoutNewPassword.setError(stringError)
        textInputLayoutNewPasswordTwice.setError(stringError)
    }
    private fun hideError() {
        textInputLayoutNewPassword.setError(EMPTY_STRING)
        textInputLayoutNewPasswordTwice.setError(EMPTY_STRING)
    }
}