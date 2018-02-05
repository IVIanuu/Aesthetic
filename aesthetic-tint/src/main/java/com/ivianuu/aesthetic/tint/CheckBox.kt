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

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Build
import android.support.v4.content.ContextCompat
import android.widget.CheckBox
import com.ivianuu.aesthetic.tint.util.MaterialColorHelper
import com.ivianuu.aesthetic.tint.util.tint

@SuppressLint("PrivateResource")
fun CheckBox.tint(color: Int) {
    val sl = ColorStateList(
        arrayOf(
            intArrayOf(-android.R.attr.state_enabled),
            intArrayOf(android.R.attr.state_enabled, -android.R.attr.state_checked),
            intArrayOf(android.R.attr.state_enabled, android.R.attr.state_checked)
        ),
        intArrayOf(
            MaterialColorHelper.getControlDisabledColor(context),
            MaterialColorHelper.getControlNormalColor(context),
            color
        )
    )
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        buttonTintList = sl
    } else {
        buttonDrawable =
                ContextCompat.getDrawable(context, R.drawable.abc_btn_check_material)?.tint(color)
    }
}