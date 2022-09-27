package com.example.lawyerapp.presentation.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lawyerapp.databinding.*
import com.example.lawyerapp.domain.situation.SituationItem
import com.example.lawyerapp.presentation.adapter.SearchBySituationAdapter
import com.example.lawyerapp.presentation.helpers.OnEditingFinishedListener


class SearchBySituationFragment: Fragment() {

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private lateinit var situationListAdapter: SearchBySituationAdapter

    private var _binding: FragmentSearchBySituationBinding? = null
    private val binding: FragmentSearchBySituationBinding
        get() = _binding ?: throw RuntimeException("FragmentAboutApplicationBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBySituationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listArraySituation: ArrayList<SituationItem> = ArrayList()
        setupRecyclerView()

        val urlImg1 = getURLForResource(com.example.lawyerapp.R.drawable.auto_s1)
        val urlImg2 = getURLForResource(com.example.lawyerapp.R.drawable.appliances_s2)
        val urlImg3 = getURLForResource(com.example.lawyerapp.R.drawable.new_buildings_s3)
        val urlImg4 = getURLForResource(com.example.lawyerapp.R.drawable.furniture_s4)
        val urlImg5 = getURLForResource(com.example.lawyerapp.R.drawable.medical_services_s5)
        val urlImg6 = getURLForResource(com.example.lawyerapp.R.drawable.clothing_s6)
        listArraySituation.add(SituationItem(0, "Автомобили", urlImg1.toString()))
        listArraySituation.add(SituationItem(1, "Бытовая техника", urlImg2.toString()))
        listArraySituation.add(SituationItem(2, "Новостройки", urlImg3.toString()))
        listArraySituation.add(SituationItem(3, "Мебель", urlImg4.toString()))
        listArraySituation.add(SituationItem(4, "Медицинские услуги", urlImg5.toString()))
        listArraySituation.add(SituationItem(5, "Одежда", urlImg6.toString()))
/*
        for (item in 6..85) {
            val situation = SituationItem(item, "cars name"+ item.toString(), urlImg5.toString())
            listArraySituation.add(situation)
        }*/
        situationListAdapter.submitList(listArraySituation)
    }

    fun getURLForResource(resourceId: Int): String? {
        //use BuildConfig.APPLICATION_ID instead of R.class.getPackage().getName() if both are not same
        return Uri.parse(
            "android.resource://" + com.example.lawyerapp.R::class.java.getPackage().getName() + "/" + resourceId
        ).toString()
    }

    private fun setupRecyclerView() {
        with(binding.rvSituationList) {
            situationListAdapter = SearchBySituationAdapter()
            adapter = situationListAdapter
            recycledViewPool.setMaxRecycledViews(
                SearchBySituationAdapter.VIEW_TYPE_ENABLED,
                SearchBySituationAdapter.MAX_POOL_SIZE
            )

        }
    }


}