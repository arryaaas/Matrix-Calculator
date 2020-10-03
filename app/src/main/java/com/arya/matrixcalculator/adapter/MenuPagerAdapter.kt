package com.arya.matrixcalculator.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.arya.matrixcalculator.ui.fragment.MenuFragment

class MenuPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return MenuFragment.newInstance(
            position + 1
        )
    }

    override fun getCount(): Int {
        return 2
    }
}