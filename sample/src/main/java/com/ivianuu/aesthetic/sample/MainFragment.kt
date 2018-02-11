package com.ivianuu.aesthetic.sample

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.PopupMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.ivianuu.aesthetic.Aesthetic
import com.ivianuu.aesthetic.mode.BottomNavBgMode
import com.ivianuu.aesthetic.mode.BottomNavIconTextMode
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {

    private val aesthetic by lazy { Aesthetic.get(context!!) }

    private var snackbar: Snackbar? = null
    private var isDarkSubscription: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Further view setup
        val spinnerAdapter = ArrayAdapter(
            context!!,
            R.layout.list_item_spinner,
            arrayOf(
                "Spinner One",
                "Spinner Two",
                "Spinner Three",
                "Spinner Four",
                "Spinner Five",
                "Spinner Six"
            )
        )
        spinnerAdapter.setDropDownViewResource(R.layout.list_item_spinner_dropdown)
        spinner.adapter = spinnerAdapter

        switch_theme.setOnClickListener {
            PopupMenu(activity!!, it).apply {
                inflate(R.menu.themes)
                setOnMenuItemClickListener {
                    val theme = when (it.itemId) {
                        R.id.light -> R.style.AppTheme
                        R.id.dark -> R.style.AppThemeDark
                        R.id.black -> R.style.AppThemeBlack
                        else -> throw IllegalArgumentException()
                    }

                    aesthetic.edit()
                        .activityTheme(theme)
                        .isDark(theme != R.style.AppTheme)
                        .apply()

                    true
                }

                show()
            }
        }

        btn_black.setOnClickListener {
            aesthetic.edit()
                .primaryColor(Color.BLACK)
                .accentColorRes(R.color.md_purple)
                .statusBarColorAuto()
                .navigationBarColorAuto()
                .bottomNavBgMode(BottomNavBgMode.PRIMARY)
                .bottomNavIconTextMode(BottomNavIconTextMode.BLACK_WHITE_AUTO)
                .apply()
        }

        btn_red.setOnClickListener {
            aesthetic.edit()
                .primaryColorRes(R.color.md_red)
                .accentColorRes(R.color.md_amber)
                .statusBarColorAuto()
                .navigationBarColorAuto()
                .bottomNavBgMode(BottomNavBgMode.PRIMARY)
                .bottomNavIconTextMode(BottomNavIconTextMode.BLACK_WHITE_AUTO)
                .apply()
        }

        btn_purple.setOnClickListener {
            aesthetic.edit()
                .primaryColorRes(R.color.md_purple)
                .accentColorRes(R.color.md_lime)
                .statusBarColorAuto()
                .navigationBarColorAuto()
                .bottomNavBgMode(BottomNavBgMode.PRIMARY)
                .bottomNavIconTextMode(BottomNavIconTextMode.BLACK_WHITE_AUTO)
                .apply()
        }

        btn_blue.setOnClickListener {
            aesthetic.edit()
                .primaryColorRes(R.color.md_blue)
                .accentColorRes(R.color.md_pink)
                .statusBarColorAuto()
                .navigationBarColorAuto()
                .bottomNavBgMode(BottomNavBgMode.PRIMARY)
                .bottomNavIconTextMode(BottomNavIconTextMode.BLACK_WHITE_AUTO)
                .apply()
        }

        btn_green.setOnClickListener {
            aesthetic.edit()
                .primaryColorRes(R.color.md_green)
                .accentColorRes(R.color.md_blue_grey)
                .statusBarColorAuto()
                .navigationBarColorAuto()
                .bottomNavBgMode(BottomNavBgMode.PRIMARY)
                .bottomNavIconTextMode(BottomNavIconTextMode.BLACK_WHITE_AUTO)
                .apply()
        }

        btn_white.setOnClickListener {
            aesthetic.edit()
                .reset()
                .primaryColorRes(R.color.md_white)
                .accentColorRes(R.color.md_blue)
                .statusBarColorAuto()
                .navigationBarColorAuto()
                .bottomNavBgMode(BottomNavBgMode.PRIMARY)
                .bottomNavIconTextMode(BottomNavIconTextMode.SELECTED_ACCENT)
                .apply()
        }

        fab.setOnClickListener {
            snackbar?.dismiss()
            snackbar = Snackbar.make(root, R.string.hello_world, Snackbar.LENGTH_LONG)
                .setAction(android.R.string.cancel) {}
                .apply { show() }
        }

        btn_dialog.setOnClickListener {
            Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        isDarkSubscription?.dispose()
        super.onDestroyView()
    }
}
