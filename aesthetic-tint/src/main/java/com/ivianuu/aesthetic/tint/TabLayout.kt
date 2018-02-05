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
import android.support.design.widget.TabLayout
import android.view.View
import com.ivianuu.aesthetic.tint.util.*

fun TabLayout.tint(
    bgColor: Int,
    indicatorColor: Int,
    activeColor: Int = MaterialColorHelper.getPrimaryTextColor(context, bgColor),
    inactiveColor: Int = MaterialColorHelper.getSecondaryTextColor(context, bgColor)
) {
    setBackgroundColor(bgColor)

    val colorStateList = getDisabledColorStateList(activeColor, inactiveColor)

    (0 until tabCount).mapNotNull { getTabAt(it) }
        .forEach {
            it.icon?.tint(colorStateList)
            try {
                val viewField = it::class.getField("mView")
                val tabView = viewField.get(it) as View
                tabView.background =
                        RippleDrawableHelper.getRippleDrawable(context, bgColor.isDark())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    setTabTextColors(inactiveColor, activeColor)
    setSelectedTabIndicatorColor(indicatorColor)
}