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

package com.ivianuu.aesthetic.theming

import android.graphics.drawable.Drawable
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import com.ivianuu.aesthetic.theming.util.getField
import com.ivianuu.aesthetic.theming.util.getIconColor
import com.ivianuu.aesthetic.theming.util.isDark
import com.ivianuu.aesthetic.theming.util.tintedNullable

fun Toolbar.tint(bgColor: Int,
                 activeColor: Int = context.getIconColor(bgColor.isDark())) {
    setBackgroundColor(bgColor)
    setTitleTextColor(activeColor)
    overflowIcon?.tintedNullable(activeColor)
    navigationIcon?.tintedNullable(activeColor)

    // The collapse icon displays when action views are expanded (e.g. SearchView)
    try {
        val field = Toolbar::class.getField("mCollapseIcon")
        (field.get(this) as Drawable?)?.tintedNullable(
            activeColor)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    (0 until menu.size())
        .map { menu.getItem(it) }
        .forEach {
            it.icon?.tintedNullable(activeColor)
            val actionView = it.actionView
            if (actionView != null && actionView is SearchView) {
                actionView.tint(bgColor, activeColor)
            }
        }}