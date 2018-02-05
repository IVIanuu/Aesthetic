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

package com.ivianuu.aesthetic.tint.util

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.ivianuu.aesthetic.tint.R

object MaterialColorHelper {

    fun getButtonDisabledColor(context: Context): Int {
        return getButtonDisabledColor(context, isDarkTheme(context))
    }

    fun getButtonDisabledColor(context: Context, color: Int): Int {
        return getButtonDisabledColor(context, color.isDark())
    }

    fun getButtonDisabledColor(context: Context, dark: Boolean): Int {
        return context.color(
            if (dark) {
                R.color.md_button_disabled_dark
            } else {
                R.color.md_button_disabled_light
            }
        )
    }

    fun getCardColor(context: Context): Int {
        return getCardColor(context, isDarkTheme(context))
    }

    fun getCardColor(context: Context, color: Int): Int {
        return getCardColor(context, color.isDark())
    }

    fun getCardColor(context: Context, dark: Boolean): Int {
        return context.color(
            if (dark) {
                R.color.md_cardview_bg_dark
            } else {
                R.color.md_cardview_bg_light
            }
        )
    }

    fun getControlDisabledColor(context: Context): Int {
        return getControlDisabledColor(
            context,
            isDarkTheme(context)
        )
    }

    fun getControlDisabledColor(context: Context, color: Int): Int {
        return getControlDisabledColor(
            context,
            color.isDark()
        )
    }

    fun getControlDisabledColor(context: Context, dark: Boolean): Int {
        return context.color(
            if (dark) {
                R.color.md_control_disabled_dark
            } else {
                R.color.md_control_disabled_light
            }
        )
    }

    fun getControlNormalColor(context: Context): Int {
        return getControlNormalColor(
            context,
            isDarkTheme(context)
        )
    }

    fun getControlNormalColor(context: Context, color: Int): Int {
        return getControlNormalColor(
            context,
            color.isDark()
        )
    }

    fun getControlNormalColor(context: Context, dark: Boolean): Int {
        return context.color(
            if (dark) {
                R.color.md_control_normal_dark
            } else {
                R.color.md_control_normal_light
            }
        )
    }

    fun getNavigationDrawerSelectedColor(context: Context): Int {
        return getNavigationDrawerSelectedColor(context, isDarkTheme(context))
    }

    fun getNavigationDrawerSelectedColor(context: Context, color: Int): Int {
        return getNavigationDrawerSelectedColor(context, color.isDark())
    }

    fun getNavigationDrawerSelectedColor(context: Context, dark: Boolean): Int {
        return context.color(
            if (dark) {
                R.color.md_navigation_drawer_selected_dark
            } else {
                R.color.md_navigation_drawer_selected_light
            }
        )
    }

    fun getRippleColor(context: Context): Int {
        return getRippleColor(
            context,
            isDarkTheme(context)
        )
    }

    fun getRippleColor(context: Context, color: Int): Int {
        return getRippleColor(
            context,
            color.isDark()
        )
    }

    fun getRippleColor(context: Context, dark: Boolean): Int {
        return context.color(
            if (dark) {
                R.color.ripple_material_dark
            } else {
                R.color.ripple_material_light
            }
        )
    }

    fun getPrimaryTextColor(context: Context): Int {
        return getPrimaryTextColor(
            context,
            isDarkTheme(context)
        )
    }

    fun getPrimaryTextColor(context: Context, color: Int): Int {
        return getPrimaryTextColor(
            context,
            color.isDark()
        )
    }

    fun getPrimaryTextColor(context: Context, dark: Boolean): Int {
        return context.color(
            if (dark) {
                R.color.primary_text_default_material_dark
            } else {
                R.color.primary_text_default_material_light
            }
        )
    }

    fun getPrimaryDisabledTextColor(context: Context): Int {
        return getPrimaryDisabledTextColor(
            context,
            isDarkTheme(context)
        )
    }

    fun getPrimaryDisabledTextColor(context: Context, color: Int): Int {
        return getPrimaryDisabledTextColor(
            context,
            color.isDark()
        )
    }

    fun getPrimaryDisabledTextColor(context: Context, dark: Boolean): Int {
        return context.color(
            if (dark) {
                R.color.primary_text_disabled_material_dark
            } else {
                R.color.primary_text_disabled_material_light
            }
        )
    }

    fun getSecondaryTextColor(context: Context): Int {
        return getSecondaryTextColor(
            context,
            isDarkTheme(context)
        )
    }

    fun getSecondaryTextColor(context: Context, color: Int): Int {
        return getSecondaryTextColor(
            context,
            color.isDark()
        )
    }

    fun getSecondaryTextColor(context: Context, dark: Boolean): Int {
        return context.color(
            if (dark) {
                R.color.secondary_text_default_material_dark
            } else {
                R.color.secondary_text_default_material_light
            }
        )
    }

    fun getSecondaryDisabledTextColor(context: Context): Int {
        return getSecondaryDisabledTextColor(
            context,
            isDarkTheme(context)
        )
    }

    fun getSecondaryDisabledTextColor(context: Context, color: Int): Int {
        return getSecondaryDisabledTextColor(
            context,
            color.isDark()
        )
    }

    fun getSecondaryDisabledTextColor(context: Context, dark: Boolean): Int {
        return context.color(
            if (dark) {
                R.color.secondary_text_disabled_material_dark
            } else {
                R.color.secondary_text_disabled_material_light
            }
        )
    }

    fun getIconColor(context: Context): Int {
        return getIconColor(context, isDarkTheme(context))
    }

    fun getIconColor(context: Context, color: Int): Int {
        return getIconColor(context, color.isDark())
    }

    fun getIconColor(context: Context, dark: Boolean): Int {
        return context.color(
            if (dark) {
                R.color.md_icon_dark
            } else {
                R.color.md_icon_light
            }
        )
    }

    fun getInactiveIconColor(context: Context): Int {
        return getInactiveIconColor(context, isDarkTheme(context))
    }

    fun getInactiveIconColor(context: Context, color: Int): Int {
        return getInactiveIconColor(context, color.isDark())
    }

    fun getInactiveIconColor(context: Context, dark: Boolean): Int {
        return context.color(
            if (dark) {
                R.color.md_icon_dark_inactive
            } else {
                R.color.md_icon_light_inactive
            }
        )
    }

    fun getBottomNavBgColor(context: Context): Int {
        return getBottomNavBgColor(context)
    }

    fun getBottomNavBgColor(context: Context, color: Int): Int {
        return getBottomNavBgColor(context, color.isDark())
    }

    fun getBottomNavBgColor(context: Context, dark: Boolean): Int {
        return context.color(
            if (dark) {
                R.color.md_bottom_nav_default_dark_bg
            } else {
                R.color.md_bottom_nav_default_light_bg
            }
        )
    }

    fun getSwitchThumbDisabledColor(context: Context): Int {
        return getSwitchThumbDisabledColor(
            context,
            isDarkTheme(context)
        )
    }

    fun getSwitchThumbDisabledColor(context: Context, color: Int): Int {
        return getSwitchThumbDisabledColor(
            context,
            color.isDark()
        )
    }

    fun getSwitchThumbDisabledColor(context: Context, dark: Boolean): Int {
        return context.color(
            if (dark) {
                R.color.switch_thumb_disabled_material_dark
            } else {
                R.color.switch_thumb_disabled_material_light
            }
        )
    }

    fun getSwitchThumbNormalColor(context: Context): Int {
        return getSwitchThumbNormalColor(
            context,
            isDarkTheme(context)
        )
    }

    fun getSwitchThumbNormalColor(context: Context, color: Int): Int {
        return getSwitchThumbNormalColor(
            context,
            color.isDark()
        )
    }

    fun getSwitchThumbNormalColor(context: Context, dark: Boolean): Int {
        return context.color(
            if (dark) {
                R.color.switch_thumb_normal_material_dark
            } else {
                R.color.switch_thumb_normal_material_light
            }
        )
    }

    fun getSwitchTrackDisabledColor(context: Context): Int {
        return getSwitchTrackDisabledColor(context, isDarkTheme(context))
    }

    fun getSwitchTrackDisabledColor(context: Context, color: Int): Int {
        return getSwitchTrackDisabledColor(context, color.isDark())
    }

    fun getSwitchTrackDisabledColor(context: Context, dark: Boolean): Int {
        return context.color(
            if (dark) {
                R.color.md_switch_track_disabled_dark
            } else {
                R.color.md_switch_track_disabled_light
            }
        )
    }

    fun getSwitchTrackNormalColor(context: Context): Int {
        return getSwitchTrackNormalColor(context, isDarkTheme(context))
    }

    fun getSwitchTrackNormalColor(context: Context, color: Int): Int {
        return getSwitchTrackNormalColor(context, color.isDark())
    }

    fun getSwitchTrackNormalColor(context: Context, dark: Boolean): Int {
        return context.color(
            if (dark) {
                R.color.md_switch_track_normal_dark
            } else {
                R.color.md_switch_track_normal_light
            }
        )
    }

    fun isDarkTheme(context: Context): Boolean {
        return context.resolveColor(android.R.attr.windowBackground).isDark()
    }

    private fun Context.resolveColor(attr: Int): Int {
        val a = theme.obtainStyledAttributes(intArrayOf(attr))
        return try {
            a.getColor(0, 0)
        } catch (ignored: Throwable) {
            0
        } finally {
            a.recycle()
        }
    }

    private fun Context.color(resId: Int) = ContextCompat.getColor(this, resId)

    private fun Int.isDark() = !isLight()

    private fun Int.isLight(): Boolean {
        if (this == Color.BLACK) {
            return false
        } else if (this == Color.WHITE || this == Color.TRANSPARENT) {
            return true
        }
        val darkness =
            1 - (0.299 * Color.red(this) + 0.587 * Color.green(this) + 0.114 * Color.blue(this)) / 255
        return darkness < 0.4
    }
}