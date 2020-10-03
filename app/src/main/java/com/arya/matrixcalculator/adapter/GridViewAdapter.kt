package com.arya.matrixcalculator.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.arya.matrixcalculator.R

class GridViewAdapter (private val context: Context, private var matrix: Array<Array<Float>>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return layoutInflater.inflate(R.layout.grid_view_item, null)
    }

    override fun getItem(position: Int): Any {
        return matrix[position%matrix.size][position-(position-position%matrix.size)]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return matrix.size * matrix[0].size
    }

}