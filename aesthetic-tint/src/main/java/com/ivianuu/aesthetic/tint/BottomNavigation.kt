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

package com.ivianuu.aesthetic.tint

import android.content.res.ColorStateList
import android.graphics.Color
import android.support.design.internal.BottomNavigationItemView
import android.support.design.widget.BottomNavigationView
import com.ivianuu.aesthetic.tint.util.*

fun BottomNavigationView.tint(
    bgColor: Int,
    selectedColor: Int = if (bgColor.isLight()) Color.BLACK else Color.WHITE
) {
    setBackgroundColor(bgColor)

    val baseColor = MaterialColorHelper.getSecondaryTextColor(context, bgColor.isDark())
    val colorStateList = ColorStateList(
        arrayOf(
            intArrayOf(-android.R.attr.state_checked),
            intArrayOf(android.R.attr.state_checked)
        ),
        intArrayOf(baseColor.adjustAlpha(0.87f), selectedColor)
    )

    itemIconTintList = colorStateList
    itemTextColor = colorStateList

    try {
        val menuViewField = BottomNavigationView::class.getField("mMenuView")
        val menuView = menuViewField.get(this)
        val buttonsField = menuView::class.getField("mButtons")
        val buttons = buttonsField.get(menuView) as Array<BottomNavigationItemView>
        buttons.forEach {
            it.background =
                    RippleDrawableHelper.getBorderlessRippleDrawable(context, bgColor.isDark())
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}