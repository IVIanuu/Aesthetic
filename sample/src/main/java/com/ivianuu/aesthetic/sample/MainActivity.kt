package com.ivianuu.aesthetic.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.util.Log
import com.ivianuu.aesthetic.Aesthetic
import com.ivianuu.aesthetic.mode.BottomNavBgMode
import com.ivianuu.aesthetic.mode.BottomNavIconTextMode
import com.ivianuu.aesthetic.mode.NavigationViewMode
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var aesthetic: Aesthetic

    override fun onCreate(savedInstanceState: Bundle?) {
        aesthetic = Aesthetic.attach(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.inflateMenu(R.menu.main)
        val searchItem = toolbar.menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search_view_example)

        if (aesthetic.isFirstTime()) {
            aesthetic.edit()
                .activityTheme(R.style.AppTheme)
                .navigationViewMode(NavigationViewMode.SELECTED_ACCENT)
                .bottomNavBgMode(BottomNavBgMode.PRIMARY)
                .bottomNavIconTextMode(BottomNavIconTextMode.SELECTED_ACCENT)
                .apply()
            recreate()
        }

        pager.adapter = MainPagerAdapter(supportFragmentManager)
        tabs.setupWithViewPager(pager)
    }
}
