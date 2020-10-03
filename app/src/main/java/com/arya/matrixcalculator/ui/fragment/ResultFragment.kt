package com.arya.matrixcalculator.ui.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.GridView
import android.widget.Toast
import com.arya.matrixcalculator.R
import com.arya.matrixcalculator.adapter.GridViewAdapter
import com.arya.matrixcalculator.logic.InversOperation
import com.arya.matrixcalculator.logic.MatrixOperation
import com.arya.matrixcalculator.ui.activity.CalculateActivity
import kotlinx.android.synthetic.main.fragment_result.*
import kotlinx.android.synthetic.main.fragment_result.btn_clear

class ResultFragment : Fragment() {

    private var trace: Float = 0F
    private var determinant: Float = 0F
    private var scalar: Float = 0F
    private var matrix: Array<Array<Float>> = Array(2) {Array(2) {0f} }
    private lateinit var adapter: GridViewAdapter
    private lateinit var gridViewMatrixResult: GridView
    private lateinit var type: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        type = CalculateActivity.type
        btn_calculate.text = type
        btn_result.isEnabled = false
        gridViewMatrixResult = view.findViewById(R.id.grid_view_matrix_result)

        if (type == "Determinant" || type == "Trace") {
            et_result.visibility = View.VISIBLE
            tv_result.visibility = View.VISIBLE
            tv_result.text = if (type == "Determinant") "det(A) = " else "tr(A) = "
        } else {
            setupMatrixEntry(view)
        }

        val message1 = if (type == "Add" || type == "Subtract" || type == "Multiply") {
            "Matrix A and Matrix B must be complete"
        } else if (type == "Determinant" || type == "Trace" || type == "Invers" || type == "Transpose") {
            "Matrix A must be complete"
        } else {
            "Matrix A and Scalar K must be complete"
        }

        val message2 = if (type == "Add" || type == "Subtract") {
            "The size of both matrix must be same"
        } else if (type == "Multiply") {
            "Size of column Matrix A and row Matrix B must be same"
        } else if (type == "Determinant" || type == "Trace" || type == "Invers") {
            "Matrix A must be square"
        } else {
            "" // for transpose and scalar multipy
        }

        btn_clear.setOnClickListener {
            clearMatrix(matrix)
            adapter.notifyDataSetChanged()
            btn_calculate.setBackgroundResource(R.drawable.bg_btn)
            btn_calculate.setTextColor(Color.parseColor("#3C4473"))
        }

        btn_calculate.setOnClickListener {
            var matrixA = MatrixAFragment.matrix
            var matrixB = if (type == "Add" || type == "Subtract" || type == "Multiply") MatrixBFragment.matrix else Array(2) {Array(2) {0f} }
            val isMatrixAEmpty = checkMatrix("A")
            val isMatrixBEmpty = if (type == "Add" || type == "Subtract" || type == "Multiply") checkMatrix("B") else false
            val isScalarKEmpty = if (type == "Scalar Multiply") checkScalar() else false

            if (!isMatrixAEmpty) {
                readMatrix("A")
                matrixA = MatrixAFragment.matrix
            }

            if (type == "Add" || type == "Subtract" || type == "Multiply") {
                if (!isMatrixBEmpty) {
                    readMatrix("B")
                    matrixB = MatrixBFragment.matrix
                }
            }

            if (type == "Scalar Multiply") {
                if (!isScalarKEmpty) {
                    readScalar()
                }
            }

            val condition1 = if (type == "Add" || type == "Subtract" || type == "Multiply") {
                (!isMatrixAEmpty && !isMatrixBEmpty)
            } else if (type == "Determinant" || type == "Trace" || type == "Invers" || type == "Transpose") {
                (!isMatrixAEmpty)
            } else {
                (!isMatrixAEmpty && !isScalarKEmpty)
            }

            val condition2 = if (type == "Add" || type == "Subtract") {
                (matrixA.size == matrixB.size && matrixA[0].size == matrixB[0].size)
            } else if (type == "Multiply") {
                (matrixA[0].size == matrixB.size)
            } else if (type == "Determinant" || type == "Trace" || type == "Invers") {
                (matrixA.size== matrixA[0].size)
            } else {
                false // for transpose and scalar multipy
            }

            when (type) {
                "Transpose", "Scalar Multiply" -> {
                    if (condition1) {
                        setupMatrixEntry(view)
                        btn_result.isEnabled = true
                    } else {
                        setToast(view, message1)
                    }
                }
                "Determinant", "Trace" -> {
                    btn_result.isEnabled = true
                }
                else -> {
                    if (condition1) {
                        if (condition2) {
                            setupMatrixEntry(view)
                            btn_result.isEnabled = true
                        } else {
                            setToast(view, message2)
                        }
                    } else {
                        setToast(view, message1)
                    }
                }
            }

            if (btn_result.isEnabled) {
                btn_calculate.setBackgroundResource(R.drawable.bg_btn_select)
                btn_calculate.setTextColor(Color.parseColor("#FFFFFF"))
            } else {
                btn_calculate.setBackgroundResource(R.drawable.bg_btn)
            }
        }

        btn_result.setOnClickListener {
            var matrixA = MatrixAFragment.matrix
            var matrixB = if (type == "Add" || type == "Subtract" || type == "Multiply") MatrixBFragment.matrix else Array(2) {Array(2) {0f} }
            val isMatrixAEmpty = checkMatrix("A")
            val isMatrixBEmpty = if (type == "Add" || type == "Subtract" || type == "Multiply") checkMatrix("B") else false
            val isScalarKEmpty = if (type == "Scalar Multiply") checkScalar() else false

            if (!isMatrixAEmpty) {
                readMatrix("A")
                matrixA = MatrixAFragment.matrix
            }

            if (type == "Add" || type == "Subtract" || type == "Multiply") {
                if (!isMatrixBEmpty) {
                    readMatrix("B")
                    matrixB = MatrixBFragment.matrix
                }
            }

            if (type == "Scalar Multiply") {
                if (!isScalarKEmpty) {
                    readScalar()
                }
            }

            when (type) {
                "Add" -> MatrixOperation.add(matrixA, matrixB, matrix)
                "Subtract" -> MatrixOperation.subtract(matrixA, matrixB, matrix)
                "Multiply" -> MatrixOperation.multiply(matrixA, matrixB, matrix)
                "Determinant" -> determinant = MatrixOperation.determinant(matrixA)
                "Trace" -> trace = MatrixOperation.trace(matrixA)
                "Invers" -> InversOperation.mainInverse(matrixA, matrix)
                "Transpose" -> MatrixOperation.transpose(matrixA, matrix)
                "Scalar Multiply" -> MatrixOperation.scalar(matrixA, scalar, matrix)
            }

            showResult()
        }
    }

    private fun setToast(view: View, msg: String) {
        Toast.makeText(view.context, msg, Toast.LENGTH_LONG).show()
    }

    private fun setupMatrixEntry(view: View) {
        val rows = if (type == "Add" || type == "Subtract" || type == "Multiply" || type == "Invers" || type == "Scalar Multiply") {
            MatrixAFragment.matrix.size
        } else {
            MatrixAFragment.matrix[0].size
        }

        val columns = if (type == "Add" || type == "Subtract" || type == "Invers" || type == "Scalar Multiply") {
            MatrixAFragment.matrix[0].size
        } else if (type == "Multiply") {
            MatrixBFragment.matrix[0].size
        } else {
            MatrixAFragment.matrix.size
        }

        matrix = Array(rows) {Array(columns) {0f} }
        adapter = GridViewAdapter(
            view.context,
            matrix
        )
        gridViewMatrixResult.numColumns = columns
        gridViewMatrixResult.adapter = adapter
    }

    private fun clearMatrix(matrix: Array<Array<Float>>) {
        when (type) {
            "Determinant" -> {
                determinant = 0F
                et_result.setText("")
            }
            "Trace" -> {
                trace = 0F
                et_result.setText("")
            }
            else -> {
                for (i in matrix.indices)
                    for (j in matrix[i].indices)
                        matrix[i][j] = 0f
            }
        }
    }

    private fun checkMatrix(type: String): Boolean {
        val matrix = if (type == "A") MatrixAFragment.matrix else MatrixBFragment.matrix
        val gridViewMatrix = if (type == "A") MatrixAFragment.gridViewMatrixA else MatrixBFragment.gridViewMatrixB

        var itemView: View
        var editText: EditText

        for (i in matrix.indices)
            for (j in matrix[i].indices) {
                itemView = gridViewMatrix.getChildAt(i * matrix[i].size + j)
                editText = itemView.findViewById(R.id.et_item)
                if (editText.text.toString().isEmpty()) return true
            }
        return false
    }

    private fun readMatrix(type: String) {
        val matrix = if (type == "A") MatrixAFragment.matrix else MatrixBFragment.matrix
        val gridViewMatrix = if (type == "A") MatrixAFragment.gridViewMatrixA else MatrixBFragment.gridViewMatrixB

        var itemView: View
        var editText: EditText

        for (i in matrix.indices)
            for (j in matrix[i].indices) {
                itemView = gridViewMatrix.getChildAt(i * matrix[i].size + j)
                editText = itemView.findViewById(R.id.et_item)
                matrix[i][j] = editText.text.toString().toFloat()
            }

        if (type == "A") MatrixAFragment.matrix = matrix.clone() else MatrixBFragment.matrix = matrix.clone()
    }

    private fun checkScalar(): Boolean {
        val itemView =
            ScalarKFragment.constraintLayout
        val editText = itemView.findViewById<EditText>(R.id.et_scalar)

        if (editText.text.toString().isEmpty()) return true
        return false
    }

    private fun readScalar() {
        val itemView =
            ScalarKFragment.constraintLayout
        val editText = itemView.findViewById<EditText>(R.id.et_scalar)
        scalar = editText.text.toString().toFloat()
    }

    private fun showResult() {
        when (type) {
            "Determinant" -> et_result.setText(getString(R.string.fromat).format(determinant))
            "Trace" -> et_result.setText(trace.toString())
            else -> {
                var itemView: View
                var editText: EditText

                for (i in matrix.indices)
                    for (j in matrix[i].indices) {
                        itemView = gridViewMatrixResult.getChildAt(i * matrix[i].size + j)
                        editText = itemView.findViewById(R.id.et_item)
                        editText.setText(matrix[i][j].toString())
                    }
            }
        }
    }

}