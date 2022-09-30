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

class CreateSituationFurnitureFreeFragment: Fragment() {

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var _binding: FragmentSituationFurnitureS3Binding? = null
    private val binding: FragmentSituationFurnitureS3Binding
        get() = _binding ?: throw RuntimeException("FragmentSituationAutoS1Binding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSituationFurnitureS3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        val args = requireArguments()
        val mainString = args.getString(FIRST_FRAGMENT_STRING)
        val main1String = mainString?.split("///")
        val firstString = main1String!![0].toString()
        val twoString = main1String!![1].toString()

       // Toast.makeText(getActivity(), "this params 1"+ firstString, Toast.LENGTH_SHORT).show()
        //Toast.makeText(getActivity(), "this params 2"+ twoString, Toast.LENGTH_SHORT).show()
        Log.d("paramsFr", "f1"+firstString+"f2"+twoString)

        binding.enterButton.setOnClickListener {
            launchFragment(CreateSituationFinishFragment())
        }


    }

    fun launchFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_item_container, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }


    companion object {

        private const val FIRST_FRAGMENT_STRING = "first_fragment_string"
     //   private const val TWO_FRAGMENT_STRING = "first_fragment_string"

        fun newInstanceStr(string: String): CreateSituationFurnitureFreeFragment {
            return CreateSituationFurnitureFreeFragment().apply {
                arguments = Bundle().apply {
                    putString(FIRST_FRAGMENT_STRING, string)
                  //  putString(TWO_FRAGMENT_STRING, string2)
                }
            }
        }


    }
}