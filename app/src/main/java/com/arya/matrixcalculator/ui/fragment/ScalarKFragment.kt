package com.arya.matrixcalculator.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import com.arya.matrixcalculator.R
import kotlinx.android.synthetic.main.fragment_scalar_k.*

class ScalarKFragment : Fragment() {

    companion object {
        var scalar: Float = 0F
        lateinit var constraintLayout: ConstraintLayout
        lateinit var editText: EditText
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scalar_k, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        constraintLayout = view.findViewById(
            R.id.constraintlayout
        )
        editText = view.findViewById(
            R.id.et_scalar
        )

        btn_clear.setOnClickListener {
            scalar = 0F
            et_scalar.setText("")
        }
    }
}