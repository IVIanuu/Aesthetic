package com.ivianuu.aesthetic.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.ivianuu.aesthetic.Aesthetic
import kotlinx.android.synthetic.main.activity_recyclerview.*

class RecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Aesthetic.attach(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)

        toolbar.setNavigationOnClickListener { finish() }
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = MainAdapter()
    }
}