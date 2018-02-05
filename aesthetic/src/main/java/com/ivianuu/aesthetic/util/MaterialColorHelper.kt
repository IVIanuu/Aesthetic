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

package com.ivianuu.aesthetic.util

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.ivianuu.aesthetic.R

object MaterialColorHelper {

    fun getButtonDisabledColor(context: Context, dark: Boolean): Int {
        return context.getResColor(
            if (dark) {
                R.color.md_button_disabled_dark
            } else {
                R.color.md_button_disabled_light
            }
        )
    }

    fun getCardColor(context: Context, dark: Boolean): Int {
        return context.getResColor(
            if (dark) {
                R.color.md_cardview_bg_dark
            } else {
                R.color.md_cardview_bg_light
            }
        )
    }

    fun getControlDisabledColor(context: Context, dark: Boolean): Int {
        return context.getResColor(
            if (dark) {
                R.color.md_control_disabled_dark
            } else {
                R.color.md_control_disabled_light
            }
        )
    }

    fun getControlNormalColor(context: Context, dark: Boolean): Int {
        return context.getResColor(
            if (dark) {
                R.color.md_control_normal_dark
            } else {
                R.color.md_control_normal_light
            }
        )
    }
    
    fun getNavigationDrawerSelectedColor(context: Context, dark: Boolean): Int {
        return context.getResColor(
            if (dark) {
                R.color.md_navigation_drawer_selected_dark
            } else {
                R.color.md_navigation_drawer_selected_light
            }
        )
    }
    
    fun getRippleColor(context: Context, dark: Boolean): Int {
        return context.getResColor(
            if (dark) {
                R.color.ripple_material_dark
            } else {
                R.color.ripple_material_light
            }
        )
    }

    fun getPrimaryTextColor(context: Context, dark: Boolean): Int {
        return context.getResColor(
            if (dark) {
                R.color.primary_text_default_material_dark
            } else {
                R.color.primary_text_default_material_light
            }
        )
    }

    fun getPrimaryDisabledTextColor(context: Context, dark: Boolean): Int {
        return context.getResColor(
            if (dark) {
                R.color.primary_text_disabled_material_dark
            } else {
                R.color.primary_text_disabled_material_light
            }
        )
    }

    fun getSecondaryTextColor(context: Context, dark: Boolean): Int {
        return context.getResColor(
            if (dark) {
                R.color.secondary_text_default_material_dark
            } else {
                R.color.secondary_text_default_material_light
            }
        )
    }

    fun getSecondaryDisabledTextColor(context: Context, dark: Boolean): Int {
        return context.getResColor(
            if (dark) {
                R.color.secondary_text_disabled_material_dark
            } else {
                R.color.secondary_text_disabled_material_light
            }
        )
    }

    fun getIconColor(context: Context, dark: Boolean): Int {
        return context.getResColor(
            if (dark) {
                R.color.md_icon_dark
            } else {
                R.color.md_icon_light
            }
        )
    }

    fun getInactiveIconColor(context: Context, dark: Boolean): Int {
        return context.getResColor(
            if (dark) {
                R.color.md_icon_dark_inactive
            } else {
                R.color.md_icon_light_inactive
            }
        )
    }

    fun getBottomNavBgColor(context: Context, dark: Boolean): Int {
        return context.getResColor(
            if (dark) {
                R.color.md_bottom_nav_default_dark_bg
            } else {
                R.color.md_bottom_nav_default_light_bg
            }
        )
    }

    fun getSwitchThumbDisabledColor(context: Context, dark: Boolean): Int {
        return context.getResColor(
            if (dark) {
                R.color.switch_thumb_disabled_material_dark
            } else {
                R.color.switch_thumb_disabled_material_light
            }
        )
    }

    fun getSwitchThumbNormalColor(context: Context, dark: Boolean): Int {
        return context.getResColor(
            if (dark) {
                R.color.switch_thumb_normal_material_dark
            } else {
                R.color.switch_thumb_normal_material_light
            }
        )
    }

    fun getSwitchTrackDisabledColor(context: Context, dark: Boolean): Int {
        return context.getResColor(
            if (dark) {
                R.color.md_switch_track_disabled_dark
            } else {
                R.color.md_switch_track_disabled_light
            }
        )
    }

    fun getSwitchTrackNormalColor(context: Context, dark: Boolean): Int {
        return context.getResColor(
            if (dark) {
                R.color.md_switch_track_normal_dark
            } else {
                R.color.md_switch_track_normal_light
            }
        )
    }
    
}