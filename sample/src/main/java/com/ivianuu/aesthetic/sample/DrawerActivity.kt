package com.ivianuu.aesthetic.sample

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.ivianuu.aesthetic.Aesthetic
import kotlinx.android.synthetic.main.activity_drawer.*

class DrawerActivity : AppCompatActivity() {

    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        Aesthetic.attach(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)
        setSupportActionBar(toolbar)

        drawerToggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawer_layout.addDrawerListener(drawerToggle)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeButtonEnabled(true)
        }

        navigation_view.post { navigation_view.setCheckedItem(R.id.item_three) }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return drawerToggle
            .onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
    }
}
