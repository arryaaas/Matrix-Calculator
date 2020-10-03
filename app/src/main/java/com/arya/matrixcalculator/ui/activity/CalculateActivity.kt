package com.arya.matrixcalculator.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.arya.matrixcalculator.*
import com.arya.matrixcalculator.adapter.SectionsPagerAdapter
import com.arya.matrixcalculator.ui.fragment.MatrixAFragment
import com.arya.matrixcalculator.ui.fragment.MatrixBFragment
import com.arya.matrixcalculator.ui.fragment.ResultFragment
import com.arya.matrixcalculator.ui.fragment.ScalarKFragment
import kotlinx.android.synthetic.main.activity_calculate.*

class CalculateActivity : AppCompatActivity() {

    companion object {
        var type: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate)

        val extras = intent.extras
        type = extras?.getString("type").toString()

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title =
                type
        }

        /*Configuring Tabs*/
        view_pager.offscreenPageLimit = 3 //to remember the state of the 3 tabs
        tabs.setupWithViewPager(view_pager)
        setUpViewPager(view_pager,
            type
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setUpViewPager(viewPager: ViewPager, type: String) {
        val sectionsPagerAdapter =
            SectionsPagerAdapter(
                supportFragmentManager
            )
        if (type == "Add" || type == "Subtract" || type == "Multiply") {
            sectionsPagerAdapter.addFragment(MatrixAFragment(), "Matrix A")
            sectionsPagerAdapter.addFragment(MatrixBFragment(), "Matrix B")
            sectionsPagerAdapter.addFragment(ResultFragment(), "Result")
        } else if (type == "Determinant" || type == "Trace" || type == "Invers" || type == "Transpose") {
            sectionsPagerAdapter.addFragment(MatrixAFragment(), "Matrix A")
            sectionsPagerAdapter.addFragment(ResultFragment(), "Result")
        } else {
            sectionsPagerAdapter.addFragment(MatrixAFragment(), "Matrix A")
            sectionsPagerAdapter.addFragment(ScalarKFragment(), "Scalar K")
            sectionsPagerAdapter.addFragment(ResultFragment(), "Result")
        }
        viewPager.adapter = sectionsPagerAdapter
    }
}