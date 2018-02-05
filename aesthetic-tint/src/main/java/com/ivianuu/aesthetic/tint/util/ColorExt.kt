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

import android.graphics.Color

fun Int.isDark() = !isLight()

fun Int.isLight(): Boolean {
    if (this == Color.BLACK) {
        return false
    } else if (this == Color.WHITE || this == Color.TRANSPARENT) {
        return true
    }
    val darkness =
        1 - (0.299 * Color.red(this) + 0.587 * Color.green(this) + 0.114 * Color.blue(this)) / 255
    return darkness < 0.4
}

fun Int.adjustAlpha(factor: Float): Int {
    val alpha = Math.round(Color.alpha(this) * factor)
    val red = Color.red(this)
    val green = Color.green(this)
    val blue = Color.blue(this)
    return Color.argb(alpha, red, green, blue)
}

fun Int.stripAlpha(): Int {
    return Color.rgb(Color.red(this), Color.green(this), Color.blue(this))
}

fun Int.darken() = shiftColor(0.9f)

fun Int.lighten() = shiftColor(1.1f)

fun Int.shiftColor(by: Float): Int {
    if (by == 1f) return this
    val hsv = FloatArray(3)
    Color.colorToHSV(this, hsv)
    hsv[2] *= by // value component
    return Color.HSVToColor(hsv)
}