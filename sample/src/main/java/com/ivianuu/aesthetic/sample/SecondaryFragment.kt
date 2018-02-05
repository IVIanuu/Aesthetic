package com.ivianuu.aesthetic.sample

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.fragment_secondary.*


class SecondaryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_secondary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drawer_layout.setOnClickListener {
            startActivity(Intent(activity, DrawerActivity::class.java))
        }

        bottom_tabs.setOnClickListener {
            startActivity(Intent(activity, BottomNavActivity::class.java))
        }

        recycler_view.setOnClickListener {
            startActivity(Intent(activity, RecyclerViewActivity::class.java))
        }
    }
}
