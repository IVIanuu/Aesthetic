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
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.widget.Button
import com.ivianuu.aesthetic.tint.util.MaterialColorHelper
import com.ivianuu.aesthetic.tint.util.getDisabledColorStateList
import com.ivianuu.aesthetic.tint.util.isLight
import com.ivianuu.aesthetic.tint.util.tint

fun Button.tint(color: Int) {
    val disabled = MaterialColorHelper.getButtonDisabledColor(context)
    val rippleColor = MaterialColorHelper.getRippleColor(context, color)
    val textColor = MaterialColorHelper.getPrimaryTextColor(context, color)
    val colorStateList = getDisabledColorStateList(color, disabled)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && background is RippleDrawable) {
        val rd = background as RippleDrawable
        rd.setColor(ColorStateList.valueOf(rippleColor))
    }

    setTextColor(
        getDisabledColorStateList(
            textColor,
            MaterialColorHelper.getPrimaryTextColor(context)
        )
    )

    background?.tint(colorStateList)

    val textColorSl = ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf(-android.R.attr.state_enabled)
        ),
        intArrayOf(
            if (color.isLight()) Color.BLACK else Color.WHITE,
            if (MaterialColorHelper.isDarkTheme(context)) Color.WHITE else Color.BLACK
        )
    )

    setTextColor(textColorSl)
    // Hack around button color not updating
    isEnabled = !isEnabled
    isEnabled = !isEnabled
}