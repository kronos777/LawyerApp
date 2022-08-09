package com.example.lawyerapp.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lawyerapp.R
import com.example.lawyerapp.databinding.FragmentSigninBinding
import com.example.lawyerapp.presentation.helpers.OnEditingFinishedListener
import com.example.lawyerapp.presentation.helpers.PhoneTextFormatter

class SignInCheckFragment() : Fragment() {

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var _binding: FragmentSigninBinding? = null
    private val binding: FragmentSigninBinding
        get() = _binding ?: throw RuntimeException("FragmentSigninBinding == null")


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


        (activity as AppCompatActivity).supportActionBar?.title = "Регистрация"

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
            Toast.makeText(getContext(), "Selected: $selectedItem", Toast.LENGTH_SHORT).show()

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

    private fun showLawyerFields() {

    }

    private fun hideLawyerFields() {
        binding.tilLastname.visibility = (View.GONE)
        binding.tilPassportData.visibility = (View.GONE)
        binding.textPassportData.visibility = (View.GONE)
        binding.tilDiplomData.visibility = (View.GONE)
        binding.textDiplomData.visibility = (View.GONE)
    }




}