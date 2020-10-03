package com.arya.matrixcalculator.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arya.matrixcalculator.R
import com.arya.matrixcalculator.adapter.MenuPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val menuPagerAdapter =
            MenuPagerAdapter(
                supportFragmentManager
            )
        view_pager.adapter = menuPagerAdapter
        tabs.setupWithViewPager(view_pager)
    }
}