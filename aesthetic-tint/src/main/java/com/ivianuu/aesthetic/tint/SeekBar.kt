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

import android.os.Build
import android.widget.SeekBar
import com.ivianuu.aesthetic.tint.util.MaterialColorHelper
import com.ivianuu.aesthetic.tint.util.getDisabledColorStateList
import com.ivianuu.aesthetic.tint.util.tint

fun SeekBar.tint(color: Int) {
    val colorStateList = getDisabledColorStateList(
        color,
        MaterialColorHelper.getControlDisabledColor(context)
    )
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        thumbTintList = colorStateList
        progressTintList = colorStateList
    } else {
        progressDrawable?.tint(colorStateList)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            thumb?.tint(colorStateList)
        }
    }
}