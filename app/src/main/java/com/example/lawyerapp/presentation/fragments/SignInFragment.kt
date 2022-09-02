package com.example.lawyerapp.presentation.fragments

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lawyerapp.R
import com.example.lawyerapp.databinding.FragmentSigninBinding
import com.example.lawyerapp.presentation.helpers.FirebaseUtils
import com.example.lawyerapp.presentation.helpers.OnEditingFinishedListener
import com.example.lawyerapp.presentation.helpers.PhoneTextFormatter
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInFragment() : Fragment(), OnEditingFinishedListener {


    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var _binding: FragmentSigninBinding? = null
    private val binding: FragmentSigninBinding
        get() = _binding ?: throw RuntimeException("FragmentSigninBinding == null")
    private lateinit var auth: FirebaseAuth

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
       //parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSigninBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        (activity as AppCompatActivity).findViewById<Toolbar>(R.id.tool_bar).title = "Регистрация"
        toolBarLink()
        hideLawyerFields()

        val items = listOf("Клиент", "Юрист")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item_role, items)
        (binding.etRole as? AutoCompleteTextView)?.setAdapter(adapter)

        // Минимальное число символов для показа выпадающего списка
        binding.etRole.threshold = 2

        // Обработчик щелчка
        binding.etRole.onItemClickListener = AdapterView.OnItemClickListener { parent, _,
                                                                               position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            // Выводим выбранное слово
            //Toast.makeText(getContext(), "Selected: $selectedItem", Toast.LENGTH_SHORT).show()
            if(selectedItem == "Юрист") {
                showLawyerFields()
            } else {
                hideLawyerFields()
            }


        }

        binding.etPhone.addTextChangedListener(PhoneTextFormatter(binding.etPhone, "+7 (###) ###-####"))

        /*
        // Отслеживаем закрытие выпадающего списка
        autoCompleteTextView.setOnDismissListener {
            Toast.makeText(applicationContext, "Suggestion closed.", Toast.LENGTH_SHORT).show()
        }

        // Обработчик щелчка для корневого элемента макета (LinearLayout или др.)
        root_layout.setOnClickListener {
            val text = autoCompleteTextView.text
            Toast.makeText(applicationContext, "Inputted: $text", Toast.LENGTH_SHORT).show()
        }

        // Если к компоненту перешёл фокус
        autoCompleteTextView.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // Выводим выпадающий список
                autoCompleteTextView.showDropDown()
            }
        }
        addTextChangeListeners()
        launchRightMode()
        observeViewModel()*/
    }

    private fun toolBarLink() {
        val materialToolbar: MaterialToolbar = (activity as AppCompatActivity).findViewById<Toolbar>(R.id.tool_bar) as MaterialToolbar
        materialToolbar.menu.getItem(0).title = "Войти"
        materialToolbar.setOnMenuItemClickListener {
            // Toast.makeText(this, "Favorites Clsadsaicked"+it.itemId, Toast.LENGTH_SHORT).show()
            when (it.itemId) {

                R.id.registration -> {
                    launchFragment(LoginFragment())
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
            ?.addToBackStack("registration")
            ?.commit()
    }


    private fun showLawyerFields() {
        binding.tilLastname.visibility = (View.VISIBLE)
        binding.tilPassportData.visibility = (View.VISIBLE)
        binding.textPassportData.visibility = (View.VISIBLE)
        binding.tilDiplomData.visibility = (View.VISIBLE)
        binding.textDiplomData.visibility = (View.VISIBLE)
    }

    private fun hideLawyerFields() {
        binding.tilLastname.visibility = (View.GONE)
        binding.tilPassportData.visibility = (View.GONE)
        binding.textPassportData.visibility = (View.GONE)
        binding.tilDiplomData.visibility = (View.GONE)
        binding.textDiplomData.visibility = (View.GONE)
    }

    private fun signUpUser() {
        val email = "i.ziborov2018@yandex.ru"
        val pass = "123456"
        val confirmPassword = "123456"

        // check pass
        if (email.isBlank() || pass.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(getActivity(), "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }

        if (pass != confirmPassword) {
            Toast.makeText(getActivity(), "Password and Confirm Password do not match", Toast.LENGTH_SHORT)
                .show()
            return
        }
        // If all credential are correct
        // We call createUserWithEmailAndPassword
        // using auth object and pass the
        // email and pass in it.
        getActivity()?.let {
            auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(it) {
                if (it.isSuccessful) {
                    //Toast.makeText(this, "Successfully Singed Up", Toast.LENGTH_SHORT).show()
                    //   binding.textView2.text = "Successfully Singed Up"
                    getActivity()?.finish()
                } else {
                    //Toast.makeText(this, "Singed Up Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun createUserLawyerData() {

        // create a dummy data
        val hashMap = hashMapOf<String, Any>(
            "name" to "Chmel",
            "lastname" to "Lisisi",
            "gender" to "man",
            "id" to "iddata"
        )

        // use the add() method to create a document inside users collection
        FirebaseUtils().fireStoreDatabase.collection("users")
            .add(hashMap)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "Added document with ID ${it.id}")
                //   binding.textView2.text = "Added document with ID ${it.id}"
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error adding document $exception")
            }

    }

    override fun onEditingFinished() {
        TODO("Not yet implemented")
    }

}