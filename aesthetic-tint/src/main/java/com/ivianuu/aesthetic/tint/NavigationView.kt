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
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.StateListDrawable
import android.support.design.widget.NavigationView
import com.ivianuu.aesthetic.tint.util.MaterialColorHelper
import com.ivianuu.aesthetic.tint.util.adjustAlpha

fun NavigationView.tint(color: Int) {
    val baseColor = MaterialColorHelper.getPrimaryTextColor(context)
    val unselectedIconColor = baseColor.adjustAlpha(0.54f)
    val unselectedTextColor = baseColor.adjustAlpha(0.87f)
    val selectedItemBgColor = MaterialColorHelper.getNavigationDrawerSelectedColor(context)

    val iconSl = ColorStateList(
        arrayOf(
            intArrayOf(-android.R.attr.state_checked),
            intArrayOf(android.R.attr.state_checked)
        ),
        intArrayOf(unselectedIconColor, color)
    )
    val textSl = ColorStateList(
        arrayOf(
            intArrayOf(-android.R.attr.state_checked),
            intArrayOf(android.R.attr.state_checked)
        ),
        intArrayOf(unselectedTextColor, color)
    )
    itemTextColor = textSl
    itemIconTintList = iconSl

    val bgDrawable = StateListDrawable()
    bgDrawable.addState(
        intArrayOf(android.R.attr.state_checked), ColorDrawable(selectedItemBgColor)
    )

    itemBackground = bgDrawable
}