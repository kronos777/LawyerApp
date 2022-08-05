package com.example.lawyerapp.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lawyerapp.R
import com.example.lawyerapp.databinding.FragmentSigninBinding

class SignInFragment() : Fragment() {

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

        val items = listOf("Клиент", "Юрист")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item_role, items)
        (binding.etRole as? AutoCompleteTextView)?.setAdapter(adapter)
       /* addTextChangeListeners()
        launchRightMode()
        observeViewModel()*/
    }

    interface OnEditingFinishedListener {

        fun onEditingFinished()

    }

}