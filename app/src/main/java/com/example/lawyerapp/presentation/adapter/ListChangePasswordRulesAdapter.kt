package com.example.lawyerapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.lawyerapp.R
import com.example.lawyerapp.presentation.helpers.DataChangePasswordRulesModel

class ListChangePasswordRulesAdapter(private val dataSet: ArrayList<*>, mContext: Context) :
    ArrayAdapter<Any?>(mContext, R.layout.item_rules_change_password_view, dataSet) {
    private class ViewHolder {
        lateinit var txtItem: TextView
    }

    var arrayList: ArrayList<Int> = ArrayList()


    override fun getCount(): Int {
        return dataSet.size
    }
    override fun getItem(position: Int): DataChangePasswordRulesModel {
        return dataSet[position] as DataChangePasswordRulesModel
    }
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val viewHolder: ViewHolder
        val result: View
        if (convertView == null) {
            viewHolder = ViewHolder()
            convertView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_rules_change_password_view, parent, false)
            viewHolder.txtItem =
                convertView.findViewById(R.id.txtItem)

            result = convertView
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
            result = convertView
        }
        val item: DataChangePasswordRulesModel = getItem(position)
        viewHolder.txtItem.text = item.item


         return result
    }



}
