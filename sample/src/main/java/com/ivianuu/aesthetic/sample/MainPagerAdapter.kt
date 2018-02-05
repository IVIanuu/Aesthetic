package com.ivianuu.aesthetic.sample

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter


internal class MainPagerAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MainFragment()
            1 -> SecondaryFragment()
            2 -> PrefsFragment()
            else -> throw IllegalArgumentException("unknown position $position")
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Main"
            1 -> "Other"
            2 -> "Prefs"
            else -> throw IllegalArgumentException("unknown position $position")
        }
    }
}
