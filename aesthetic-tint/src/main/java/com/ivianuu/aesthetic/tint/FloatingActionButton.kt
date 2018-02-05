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
import android.support.design.widget.FloatingActionButton
import com.ivianuu.aesthetic.tint.util.MaterialColorHelper
import com.ivianuu.aesthetic.tint.util.isDark
import com.ivianuu.aesthetic.tint.util.lighten
import com.ivianuu.aesthetic.tint.util.tint

fun FloatingActionButton.tint(color: Int) {
    val dark = color.isDark()
    val pressed = color.lighten()
    val rippleColor = MaterialColorHelper.getRippleColor(context, dark)
    val textColor = MaterialColorHelper.getPrimaryTextColor(context, dark)

    val colorStateList: ColorStateList

    colorStateList = ColorStateList(
        arrayOf(
            intArrayOf(-android.R.attr.state_pressed),
            intArrayOf(android.R.attr.state_pressed)
        ),
        intArrayOf(color, pressed)
    )

    this.rippleColor = rippleColor
    backgroundTintList = colorStateList
    drawable?.tint(textColor)
    setColorFilter(if (dark) Color.WHITE else Color.BLACK)
}