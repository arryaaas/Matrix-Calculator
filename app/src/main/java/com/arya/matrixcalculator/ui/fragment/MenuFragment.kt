package com.arya.matrixcalculator.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arya.matrixcalculator.R
import com.arya.matrixcalculator.ui.activity.CalculateActivity
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : Fragment() {

    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(index: Int): MenuFragment {
            val fragment = MenuFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var index = 1
        if (arguments != null) {
            index = arguments?.getInt(ARG_SECTION_NUMBER, 0) as Int
        }

        if (index == 1) {
            btn_1.text = resources.getString(R.string.add)
            btn_2.text = resources.getString(R.string.subtract)
            btn_3.text = resources.getString(R.string.multiply)
            btn_4.text = resources.getString(R.string.determinant)

            btn_1.setOnClickListener{ moveToCalculateActivity(resources.getString(R.string.add)) }
            btn_2.setOnClickListener{ moveToCalculateActivity(resources.getString(R.string.subtract)) }
            btn_3.setOnClickListener{ moveToCalculateActivity(resources.getString(R.string.multiply)) }
            btn_4.setOnClickListener{ moveToCalculateActivity(resources.getString(R.string.determinant)) }
        } else {
            btn_1.text = resources.getString(R.string.trace)
            btn_2.text = resources.getString(R.string.inverse)
            btn_3.text = resources.getString(R.string.transpose)
            btn_4.text = resources.getString(R.string.scalar_multiply)

            btn_1.setOnClickListener{ moveToCalculateActivity(resources.getString(R.string.trace)) }
            btn_2.setOnClickListener{ moveToCalculateActivity(resources.getString(R.string.inverse)) }
            btn_3.setOnClickListener{ moveToCalculateActivity(resources.getString(R.string.transpose)) }
            btn_4.setOnClickListener{ moveToCalculateActivity(resources.getString(R.string.scalar_multiply)) }
        }

    }

    private fun moveToCalculateActivity(msg: String) {
        Intent(context, CalculateActivity::class.java).also { intent ->
            intent.putExtra("type", msg)
            startActivity(intent)
        }
    }
}