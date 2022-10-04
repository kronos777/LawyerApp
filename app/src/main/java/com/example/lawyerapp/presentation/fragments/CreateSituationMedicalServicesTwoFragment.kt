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
import com.example.lawyerapp.presentation.helpers.OnEditingFinishedListener

class CreateSituationMedicalServicesTwoFragment: Fragment() {

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var _binding: FragmentSituationMedicalServicesS2Binding? = null
    private val binding: FragmentSituationMedicalServicesS2Binding
        get() = _binding ?: throw RuntimeException("FragmentSituationAutoS1Binding == null")

    private var radioSelect: String = String()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSituationMedicalServicesS2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val args = requireArguments()
        val strFirstFragment = args.getString(FIRST_FRAGMENT_STRING)
      //  Toast.makeText(getActivity(), "this params"+ strFirstFragment, Toast.LENGTH_SHORT).show()

        val radioGroup = binding.radioGroupSituation

        radioGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = group.findViewById(checkedId)
                radioSelect = radio.text.toString()
            })


        binding.enterButton.setOnClickListener {
            Log.d("paramsFr", "f1"+strFirstFragment.toString()+"f2"+radioSelect)

            val strToFree = strFirstFragment.toString()+"///"+radioSelect
            launchFragment(CreateSituationMedicalServicesFreeFragment.newInstanceStr(strToFree))
        }

    }


    fun launchFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_item_container, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }
/*        fun newInstanceStudentId(studentId: Int): PaymentItemListFragment {
            return PaymentItemListFragment().apply {
                arguments = Bundle().apply {
                    putInt(STUDENT_ID, studentId)
                    putString(SCREEN_MODE, STUDENT_ID_LIST)
                }
            }
        }*/


    companion object {
        private const val FIRST_FRAGMENT_STRING = "first_fragment"

        fun newInstanceStr(str: String): CreateSituationMedicalServicesTwoFragment {
            return CreateSituationMedicalServicesTwoFragment().apply {
                arguments = Bundle().apply {
                    putString(FIRST_FRAGMENT_STRING, str)
                }
            }
        }

    }

}