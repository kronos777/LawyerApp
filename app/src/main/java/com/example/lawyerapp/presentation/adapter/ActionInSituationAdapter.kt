package com.example.lawyerapp.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.lawyerapp.R
import com.example.lawyerapp.databinding.ItemActionBinding
import com.example.lawyerapp.databinding.ItemSituationBinding
import com.example.lawyerapp.domain.situation.SituationItem
import com.example.lawyerapp.domain.systemaction.ActionSystemItem
import com.example.lawyerapp.presentation.adapter.viewholders.ActionSystemItemDiffCallback
import com.example.lawyerapp.presentation.adapter.viewholders.ActionSystemItemViewHolder
import com.example.lawyerapp.presentation.adapter.viewholders.SituationItemDiffCallback
import com.example.lawyerapp.presentation.adapter.viewholders.SituationItemViewHolder

class ActionInSituationAdapter: ListAdapter<ActionSystemItem, ActionSystemItemViewHolder>(
    ActionSystemItemDiffCallback()
) {

    var onPaymentItemClickListener: ((ActionSystemItem) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionSystemItemViewHolder {
        val layout = R.layout.item_action

        val binding = DataBindingUtil.inflate<ItemActionBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return ActionSystemItemViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ActionSystemItemViewHolder, position: Int) {
        val actionItem = getItem(position)
        val binding = viewHolder.binding

        binding.root.setOnClickListener {
            onPaymentItemClickListener?.invoke(actionItem)
        }

        Log.d("itemsit", actionItem.titleAction)
        /*       when (binding) {
            is ItemStudentDisabledBinding -> {
                binding.studentItem = studentItem
            }
            is ItemStudentEnabledBinding -> {
                binding.studentItem = studentItem
            }
        }*/

          binding.actionSystemItem = actionItem



    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return ActionInSituationAdapter.VIEW_TYPE_ENABLED

    }

    companion object {

        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101
        const val MAX_POOL_SIZE = 30
    }

}
