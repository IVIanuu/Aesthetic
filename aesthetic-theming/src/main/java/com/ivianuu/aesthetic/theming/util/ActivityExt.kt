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

@file:JvmName("ActivityUtil")

package com.ivianuu.aesthetic.theming.util

import android.app.Activity
import android.app.ActivityManager
import android.os.Build
import android.view.View

fun Activity.setStatusBarColorCompat(color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.statusBarColor = color
    }
}

fun Activity.setNavBarColorCompat(color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.navigationBarColor = color
    }
}

fun Activity.setLightStatusBarCompat(light: Boolean) {
    val view = window.decorView
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        var flags = view.systemUiVisibility
        flags = if (light) {
            flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
        view.systemUiVisibility = flags
    }
}

fun Activity.setLightNavBarCompat(light: Boolean) {
    val view = window.decorView
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        var flags = view.systemUiVisibility
        flags = if (light) {
            flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        } else {
            flags and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
        }
        view.systemUiVisibility = flags
    }
}

fun Activity.setTaskDescriptionColor(color: Int) {
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) return
    val color = color.stripAlpha()
    // Sets color of entry in the system recents page
    val td = ActivityManager.TaskDescription(title as String, null, color)
    setTaskDescription(td)
}
