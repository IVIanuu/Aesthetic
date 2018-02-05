package com.ivianuu.aesthetic.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ivianuu.aesthetic.Aesthetic
import kotlinx.android.synthetic.main.activity_bottom_nav.*

class BottomNavActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Aesthetic.attach(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)

        toolbar.setNavigationOnClickListener { finish() }
    }
}
