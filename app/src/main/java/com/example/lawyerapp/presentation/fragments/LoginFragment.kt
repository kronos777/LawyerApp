package com.example.lawyerapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.lawyerapp.R
import com.example.lawyerapp.databinding.FragmentLoginBinding
import com.example.lawyerapp.databinding.FragmentSigninBinding
import com.example.lawyerapp.presentation.helpers.OnEditingFinishedListener
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment: Fragment() {

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding ?: throw RuntimeException("FragmentSigninBinding == null")
    private lateinit var auth: FirebaseAuth

    //private lateinit var user: String
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
       //(activity as AppCompatActivity).supportActionBar?.title = "Войти"
       //(activity as AppCompatActivity).supportActionBar?.subtitle = "Зарегистрироваться"
       (activity as AppCompatActivity).findViewById<Toolbar>(R.id.tool_bar).title = "Войти"
        toolBarLink()

       //(activity as AppCompatActivity).findViewById<Toolbar>(R.id.registration).title= "Зарегистрироваться"


        binding.enterButton.setOnClickListener {
            email = binding.etEmail.text.toString()
            password = binding.etPassword.text.toString()

            login(email, password)
           // Toast.makeText(getActivity(), "почта" + email +  "пароль" + password,  Toast.LENGTH_SHORT).show()

        }

    }


    private fun toolBarLink() {
        val materialToolbar: MaterialToolbar = (activity as AppCompatActivity).findViewById<Toolbar>(R.id.tool_bar) as MaterialToolbar
        materialToolbar.menu.getItem(0).title = "Регистрация"
        materialToolbar.setOnMenuItemClickListener {
            // Toast.makeText(this, "Favorites Clsadsaicked"+it.itemId, Toast.LENGTH_SHORT).show()
            when (it.itemId) {

                R.id.registration -> {
                    launchFragment(SignInFragment())
                    true
                }

                else -> false
            }
        }
    }
    
    private fun launchFragment(fragment: Fragment) {
       // this.supportFragmentManager.popBackStack()
        getActivity()?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_item_container, fragment)
            ?.addToBackStack("login")
            ?.commit()
    }

    private fun login(email: String, password: String) {
        val mail = email
        val pass = password
        // calling signInWithEmailAndPassword(email, pass) "i.ziborov2018@yandex.ru" "123456"
        // function using Firebase auth object
        // On successful response Display a Toast
        getActivity()?.let {
            auth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(it) {
                if (it.isSuccessful) {
                    val user = Firebase.auth.currentUser
                    Toast.makeText(getActivity(), "Successfully LoggedIn your user id " + user?.uid.toString() + "user mail " + user?.email.toString(), Toast.LENGTH_SHORT).show()
                    //    binding.textView2.text = "Successfully login"
                } else
                    Toast.makeText(getActivity(), "Log In failed ", Toast.LENGTH_SHORT).show()
            }
        }
    }

}