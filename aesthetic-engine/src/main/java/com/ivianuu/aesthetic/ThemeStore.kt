/*
 * Copyright 2018 Manuel Wrage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ivianuu.aesthetic

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.ivianuu.aesthetic.mode.*
import com.ivianuu.aesthetic.theming.util.darken
import com.ivianuu.aesthetic.theming.util.getResColor
import com.ivianuu.aesthetic.theming.util.isLight
import com.ivianuu.aesthetic.theming.util.resolveColor

@SuppressLint("CheckResult")
class ThemeStore private constructor(private val context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val rxPrefs = RxSharedPreferences.create(prefs)

    private val activityTheme by unsafeLazy { rxPrefs.getInteger(KEY_ACTIVITY_THEME, 0) }
    private val isDark by unsafeLazy { rxPrefs.getBoolean(KEY_IS_DARK, false) }
    private val primaryColor by unsafeLazy { rxPrefs.getInteger(KEY_PRIMARY_COLOR, 0) }
    private val accentColor by unsafeLazy { rxPrefs.getInteger(KEY_ACCENT_COLOR, 0) }
    private val primaryColorDark by unsafeLazy { rxPrefs.getInteger(KEY_PRIMARY_COLOR_DARK, 0) }

    private val primaryTextColor by unsafeLazy { rxPrefs.getInteger(KEY_PRIMARY_TEXT_COLOR, 0) }
    private val secondaryTextColor by unsafeLazy { rxPrefs.getInteger(KEY_SECONDARY_TEXT_COLOR, 0) }
    private val primaryTextInverseColor by unsafeLazy { rxPrefs.getInteger(KEY_PRIMARY_TEXT_INVERSE_COLOR, 0) }
    private val secondaryTextInverseColor by unsafeLazy { rxPrefs.getInteger(KEY_SECONDARY_TEXT_INVERSE_COLOR, 0) }
    private val windowBackgroundColor by unsafeLazy { rxPrefs.getInteger(KEY_WINDOW_BG_COLOR, 0) }

    private val iconTitleActiveColor by unsafeLazy { rxPrefs.getInteger(KEY_ICON_TITLE_ACTIVE_COLOR, 0) }
    private val iconTitleInactiveColor by unsafeLazy { rxPrefs.getInteger(KEY_ICON_TITLE_INACTIVE_COLOR, 0) }

    private val statusBarColor by unsafeLazy { rxPrefs.getInteger(KEY_STATUS_BAR_COLOR, 0) }
    private val navigationBarColor by unsafeLazy { rxPrefs.getInteger(KEY_NAV_BAR_COLOR, 0) }

    private val lightStatusBarMode by unsafeLazy {
        rxPrefs.getInteger(
            KEY_LIGHT_STATUS_BAR_MODE,
            AutoSwitchMode.AUTO
        )
    }
    private val lightNavigationBarMode by unsafeLazy {
        rxPrefs.getInteger(
            KEY_LIGHT_NAVIGATION_BAR_MODE,
            AutoSwitchMode.AUTO
        )
    }

    private val bottomNavBgMode by unsafeLazy {
        rxPrefs.getInteger(
            KEY_BOTTOM_NAV_BG_MODE,
            BottomNavBgMode.BLACK_WHITE_AUTO
        )
    }
    private val bottomNavIconTextMode by unsafeLazy {
        rxPrefs.getInteger(
            KEY_BOTTOM_NAV_ICONTEXT_MODE,
            BottomNavIconTextMode.SELECTED_ACCENT
        )
    }

    private val cardViewBackgroundColor by unsafeLazy {
        rxPrefs.getInteger(
            KEY_CARD_VIEW_BG_COLOR,
            0
        )
    }

    private val navigationViewMode by unsafeLazy {
        rxPrefs.getInteger(
            KEY_NAVIGATION_VIEW_MODE,
            NavigationViewMode.SELECTED_ACCENT
        )
    }

    private val snackbarTextColor by unsafeLazy { rxPrefs.getInteger(KEY_SNACKBAR_TEXT_COLOR, 0) }
    private val snackbarActionTextColor by unsafeLazy {
        rxPrefs.getInteger(
            KEY_SNACKBAR_ACTION_TEXT_COLOR, 0
        )
    }

    private val tabLayoutBgMode by unsafeLazy {
        rxPrefs.getInteger(
            KEY_TAB_LAYOUT_BG_MODE,
            TabLayoutBgMode.PRIMARY
        )
    }
    private val tabLayoutIndicatorMode by unsafeLazy {
        rxPrefs.getInteger(
            KEY_TAB_LAYOUT_INDICATOR_MODE,
            TabLayoutIndicatorMode.ACCENT
        )
    }

    fun isFirstTime() = prefs.getBoolean(KEY_FIRST_TIME, true)

    fun activityTheme() = activityTheme.asObservable()

    fun isDark() = isDark.asObservable()

    fun primaryColor() = primaryColor.asObservable()

    fun accentColor() = accentColor.asObservable()

    fun primaryColorDark() = primaryColorDark.asObservable()

    fun primaryTextColor() = primaryTextColor.asObservable()

    fun secondaryTextColor() = secondaryTextColor.asObservable()

    fun primaryTextInverseColor() = primaryTextInverseColor.asObservable()

    fun secondaryTextInverseColor() = secondaryTextInverseColor.asObservable()

    fun windowBackgroundColor() = windowBackgroundColor.asObservable()

    fun iconTitleActiveColor() = iconTitleActiveColor.asObservable()

    fun iconTitleInactiveColor() = iconTitleInactiveColor.asObservable()

    fun statusBarColor() = statusBarColor.asObservable()

    fun navigationBarColor() = navigationBarColor.asObservable()

    fun lightStatusBarMode() = lightStatusBarMode.asObservable()

    fun lightNavigationBarMode() = lightNavigationBarMode.asObservable()

    fun bottomNavBgMode() = bottomNavBgMode.asObservable()

    fun bottomNavIconTextMode() = bottomNavIconTextMode.asObservable()

    fun cardViewBackgroundColor() = cardViewBackgroundColor.asObservable()

    fun navigationViewMode() = navigationViewMode.asObservable()

    fun snackbarTextColor() = snackbarTextColor.asObservable()

    fun snackbarActionTextColor() = snackbarActionTextColor.asObservable()

    fun tabLayoutBgMode() = tabLayoutBgMode.asObservable()

    fun tabLayoutIndicatorMode() = tabLayoutIndicatorMode.asObservable()

    @SuppressLint("CommitPrefEdits")
    fun edit() = Editor(context, prefs)

    class Editor(
        private val context: Context,
        private val prefs: SharedPreferences
    ) {

        private val editor = prefs.edit()

        fun activityTheme(theme: Int): Editor {
            editor.putInt(KEY_ACTIVITY_THEME, theme)
            return this
        }

        fun isDark(isDark: Boolean): Editor {
            editor.putBoolean(KEY_IS_DARK, isDark)
            return this
        }

        fun primaryColor(color: Int): Editor {
            editor.putInt(KEY_PRIMARY_COLOR, color).commit()
            return this
        }

        fun primaryColorRes(colorRes: Int): Editor {
            return primaryColor(context.getResColor(colorRes))
        }

        fun primaryColorAttr(colorAttr: Int): Editor {
            return primaryColor(context.resolveColor(colorAttr))
        }

        fun accentColor(color: Int): Editor {
            editor.putInt(KEY_ACCENT_COLOR, color)
            return this
        }

        fun accentColorRes(colorRes: Int): Editor {
            return accentColor(context.getResColor(colorRes))
        }

        fun accentColorAttr(colorAttr: Int): Editor {
            return accentColor(context.resolveColor(colorAttr))
        }

        fun primaryColorDark(color: Int): Editor {
            editor.putInt(KEY_PRIMARY_COLOR_DARK, color).commit()
            return this
        }

        fun primaryColorDarkRes(colorRes: Int): Editor {
            return primaryColorDark(context.getResColor(colorRes))
        }

        fun primaryColorDarkAttr(colorAttr: Int): Editor {
            return primaryColorDark(context.resolveColor(colorAttr))
        }

        fun primaryColorDarkAuto(): Editor {
            return primaryColorDark(prefs.getInt(KEY_PRIMARY_COLOR, 0).darken())
        }

        fun primaryTextColor(color: Int): Editor {
            editor.putInt(KEY_PRIMARY_TEXT_COLOR, color)
            return this
        }

        fun primaryTextColorRes(colorRes: Int): Editor {
            return primaryTextColor(context.getResColor(colorRes))
        }

        fun primaryTextColorAttr(colorAttr: Int): Editor {
            return primaryTextColor(context.resolveColor(colorAttr))
        }

        fun secondaryTextColor(color: Int): Editor {
            editor.putInt(KEY_SECONDARY_TEXT_COLOR, color)
            return this
        }

        fun secondaryTextColorRes(colorRes: Int): Editor {
            return secondaryTextColor(context.getResColor(colorRes))
        }

        fun secondaryTextColorAttr(colorAttr: Int): Editor {
            return secondaryTextColor(context.resolveColor(colorAttr))
        }

        fun primaryTextInverseColor(color: Int): Editor {
            editor.putInt(KEY_PRIMARY_TEXT_INVERSE_COLOR, color)
            return this
        }

        fun primaryTextInverseColorRes(colorRes: Int): Editor {
            return primaryTextInverseColor(context.getResColor(colorRes))
        }

        fun primaryTextInverseColorAttr(colorAttr: Int): Editor {
            return primaryTextInverseColor(context.resolveColor(colorAttr))
        }

        fun secondaryTextInverseColor(color: Int): Editor {
            editor.putInt(KEY_SECONDARY_TEXT_INVERSE_COLOR, color)
            return this
        }

        fun secondaryTextInverseColorRes(colorRes: Int): Editor {
            return secondaryTextInverseColor(context.getResColor(colorRes))
        }

        fun secondaryTextInverseColorAttr(colorAttr: Int): Editor {
            return secondaryTextInverseColor(context.resolveColor(colorAttr))
        }

        fun windowBackgroundColor(color: Int): Editor {
            editor.putInt(KEY_WINDOW_BG_COLOR, color)
            return this
        }

        fun windowBackgroundColorRes(colorRes: Int): Editor {
            return windowBackgroundColor(context.getResColor(colorRes))
        }

        fun windowBackgroundColorAttr(colorAttr: Int): Editor {
            return windowBackgroundColor(context.resolveColor(colorAttr))
        }

        fun colorIconTitleActive(color: Int): Editor {
            editor.putInt(KEY_ICON_TITLE_ACTIVE_COLOR, color)
            return this
        }

        fun colorIconTitleActiveRes(colorRes: Int): Editor {
            return colorIconTitleActive(context.getResColor(colorRes))
        }

        fun colorIconTitleActiveAttr(colorAttr: Int): Editor {
            return colorIconTitleActive(context.resolveColor(colorAttr))
        }

        fun colorIconTitleInactive(color: Int): Editor {
            editor.putInt(KEY_ICON_TITLE_INACTIVE_COLOR, color)
            return this
        }

        fun colorIconTitleInactiveRes(colorRes: Int): Editor {
            return colorIconTitleInactive(context.getResColor(colorRes))
        }

        fun colorIconTitleInactiveAttr(colorAttr: Int): Editor {
            return colorIconTitleInactive(context.resolveColor(colorAttr))
        }

        fun statusBarColor(color: Int): Editor {
            editor.putInt(KEY_STATUS_BAR_COLOR, color)
            return this
        }

        fun statusBarColorRes(colorRes: Int): Editor {
            return statusBarColor(context.getResColor(colorRes))
        }

        fun statusBarColorAttr(colorAttr: Int): Editor {
            return statusBarColor(context.resolveColor(colorAttr))
        }

        fun statusBarColorAuto(): Editor {
            return statusBarColor(prefs.getInt(KEY_PRIMARY_COLOR, 0).darken())
        }

        fun navigationBarColor(color: Int): Editor {
            editor.putInt(KEY_NAV_BAR_COLOR, color)
            return this
        }

        fun navigationBarColorRes(colorRes: Int): Editor {
            return navigationBarColor(context.getResColor(colorRes))
        }

        fun navigationBarColorAttr(colorAttr: Int): Editor {
            return navigationBarColor(context.resolveColor(colorAttr))
        }

        fun navigationBarColorAuto(): Editor {
            val primaryColor = prefs.getInt(KEY_PRIMARY_COLOR, 0).darken()
            val navBarColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                primaryColor
            } else {
                if (primaryColor.isLight()) Color.BLACK else primaryColor
            }
            return navigationBarColor(navBarColor)
        }

        fun lightStatusBarMode(mode: Int): Editor {
            editor.putInt(KEY_LIGHT_STATUS_BAR_MODE, mode)
            return this
        }

        fun lightStatusBarModeAuto(): Editor {
            val mode = if (prefs.getInt(KEY_STATUS_BAR_COLOR, 0).isLight())
                AutoSwitchMode.ON else AutoSwitchMode.OFF
            return lightStatusBarMode(mode)
        }

        fun lightNavigationBarMode(mode: Int): Editor {
            editor.putInt(KEY_LIGHT_NAVIGATION_BAR_MODE, mode)
            return this
        }

        fun lightNavigationBarModeAuto(): Editor {
            val mode = if (prefs.getInt(KEY_NAV_BAR_COLOR, 0).isLight())
                AutoSwitchMode.ON else AutoSwitchMode.OFF
            return lightStatusBarMode(mode)
        }

        fun bottomNavBgMode(bgMode: Int): Editor {
            editor.putInt(KEY_BOTTOM_NAV_BG_MODE, bgMode)
            return this
        }

        fun bottomNavIconTextMode(iconTextMode: Int): Editor {
            editor.putInt(KEY_BOTTOM_NAV_ICONTEXT_MODE, iconTextMode)
            return this
        }

        fun cardViewBackgroundColor(color: Int): Editor {
            editor.putInt(KEY_CARD_VIEW_BG_COLOR, color)
            return this
        }

        fun cardViewBackgroundColorRes(colorRes: Int): Editor {
            return cardViewBackgroundColor(context.getResColor(colorRes))
        }

        fun cardViewBackgroundColorAttr(colorAttr: Int): Editor {
            return cardViewBackgroundColor(context.resolveColor(colorAttr))
        }

        fun navigationViewMode(navigationViewMode: Int): Editor {
            editor.putInt(KEY_NAVIGATION_VIEW_MODE, navigationViewMode)
            return this
        }

        fun snackbarTextColor(color: Int): Editor {
            editor.putInt(KEY_SNACKBAR_TEXT_COLOR, color)
            return this
        }

        fun snackbarTextColorRes(colorRes: Int): Editor {
            return snackbarTextColor(context.getResColor(colorRes))
        }

        fun snackbarTextColorAttr(colorAttr: Int): Editor {
            return snackbarTextColor(context.resolveColor(colorAttr))
        }

        fun snackbarActionTextColor(color: Int): Editor {
            editor.putInt(KEY_SNACKBAR_ACTION_TEXT_COLOR, color)
            return this
        }

        fun snackbarActionTextColorRes(colorRes: Int): Editor {
            return snackbarActionTextColor(context.getResColor(colorRes))
        }

        fun snackbarActionTextColorAttr(colorAttr: Int): Editor {
            return snackbarActionTextColor(context.resolveColor(colorAttr))
        }

        fun tabLayoutBgMode(bgMode: Int): Editor {
            editor.putInt(KEY_TAB_LAYOUT_BG_MODE, bgMode)
            return this
        }

        fun tabLayoutIndicatorMode(indicatorMode: Int): Editor {
            editor.putInt(KEY_TAB_LAYOUT_INDICATOR_MODE, indicatorMode)
            return this
        }

        fun reset(): Editor {
            editor.clear()
            return this
        }

        fun apply() {
            editor.putBoolean(KEY_FIRST_TIME, false)
            editor.apply()
        }
    }

    companion object {
        private const val PREFS_NAME = "[aesthetic-prefs]"
        private const val KEY_FIRST_TIME = "first_time"
        private const val KEY_ACTIVITY_THEME = "activity_theme"
        private const val KEY_IS_DARK = "is_dark"
        private const val KEY_PRIMARY_COLOR = "primary_color"
        private const val KEY_PRIMARY_COLOR_DARK = "primary_color_dark"
        private const val KEY_ACCENT_COLOR = "accent_color"

        private const val KEY_PRIMARY_TEXT_COLOR = "primary_text_color"
        private const val KEY_SECONDARY_TEXT_COLOR = "secondary_text_color"
        private const val KEY_PRIMARY_TEXT_INVERSE_COLOR = "primary_text_inverse_color"
        private const val KEY_SECONDARY_TEXT_INVERSE_COLOR = "secondary_text_inverse_color"
        private const val KEY_WINDOW_BG_COLOR = "window_bg_color"

        private const val KEY_ICON_TITLE_ACTIVE_COLOR = "icon_title_active_color"
        private const val KEY_ICON_TITLE_INACTIVE_COLOR = "icon_title_inactive_color"

        private const val KEY_STATUS_BAR_COLOR = "status_bar_color"
        private const val KEY_NAV_BAR_COLOR = "nav_bar_color"
        private const val KEY_LIGHT_STATUS_BAR_MODE = "light_status_bar_color"
        private const val KEY_LIGHT_NAVIGATION_BAR_MODE = "light_navigation_bar_mode"

        private const val KEY_BOTTOM_NAV_BG_MODE = "bottom_nav_bg_mode"
        private const val KEY_BOTTOM_NAV_ICONTEXT_MODE = "bottom_nav_icontext_mode"

        private const val KEY_CARD_VIEW_BG_COLOR = "card_view_bg_color"

        private const val KEY_NAVIGATION_VIEW_MODE = "navigation_view_mode"

        private const val KEY_SNACKBAR_TEXT_COLOR = "snackbar_text_color"
        private const val KEY_SNACKBAR_ACTION_TEXT_COLOR = "snackbar_action_text_color"

        private const val KEY_TAB_LAYOUT_BG_MODE = "tab_layout_bg_mode"
        private const val KEY_TAB_LAYOUT_INDICATOR_MODE = "tab_layout_indicator_mode"

        @SuppressLint("StaticFieldLeak") private var themeStore: ThemeStore? = null

        fun get(context: Context): ThemeStore {
            var themeStore = themeStore
            if (themeStore == null) {
                themeStore = ThemeStore(context.applicationContext)
                Companion.themeStore = themeStore
            }

            return themeStore
        }

    }

    private fun <T> unsafeLazy(init: () -> T) = lazy(LazyThreadSafetyMode.NONE, init)
}