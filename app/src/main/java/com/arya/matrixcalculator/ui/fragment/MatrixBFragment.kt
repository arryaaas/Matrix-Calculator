package com.arya.matrixcalculator.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.GridView
import com.arya.matrixcalculator.R
import com.arya.matrixcalculator.adapter.GridViewAdapter
import kotlinx.android.synthetic.main.fragment_matrix_b.*

class MatrixBFragment : Fragment() {

    companion object {
        var matrix: Array<Array<Float>> = Array(2) {Array(2) {0f} }
        lateinit var gridViewMatrixB: GridView
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_matrix_b, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinnerSize = arrayOf(2, 3, 4, 5)
        spinner_rows.adapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_dropdown_item, spinnerSize)
        spinner_columns.adapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_dropdown_item, spinnerSize)

        gridViewMatrixB = view.findViewById(
            R.id.grid_view_matrix_b
        )

        var rows = spinner_rows.selectedItem.toString().toInt()
        var columns = spinner_columns.selectedItem.toString().toInt()
        matrix = Array(rows) {Array(columns) {0f} }
        var adapter = GridViewAdapter(
            view.context,
            matrix
        )
        setupMatrixEntry(columns, adapter)

        btn_resize.setOnClickListener {
            rows = spinner_rows.selectedItem.toString().toInt()
            columns = spinner_columns.selectedItem.toString().toInt()
            matrix = Array(rows) {Array(columns) {0f} }
            adapter = GridViewAdapter(
                view.context,
                matrix
            )
            setupMatrixEntry(columns, adapter)
        }

        btn_clear.setOnClickListener {
            clearMatrix(matrix)
            adapter.notifyDataSetChanged()
        }
    }

    private fun setupMatrixEntry(columns: Int, adapter: GridViewAdapter) {
        gridViewMatrixB.numColumns = columns
        gridViewMatrixB.adapter = adapter
    }

    private fun clearMatrix(matrix: Array<Array<Float>>) {
        for (i in matrix.indices)
            for (j in matrix[i].indices)
                matrix[i][j] = 0f
    }
}