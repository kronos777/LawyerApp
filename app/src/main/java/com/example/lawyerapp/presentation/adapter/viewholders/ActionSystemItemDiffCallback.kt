package com.example.lawyerapp.presentation.adapter.viewholders

import androidx.recyclerview.widget.DiffUtil
import com.example.lawyerapp.domain.systemaction.ActionSystemItem

class ActionSystemItemDiffCallback: DiffUtil.ItemCallback<ActionSystemItem>() {

    override fun areItemsTheSame(oldItem: ActionSystemItem, newItem: ActionSystemItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ActionSystemItem, newItem: ActionSystemItem): Boolean {
        return oldItem == newItem
    }
}