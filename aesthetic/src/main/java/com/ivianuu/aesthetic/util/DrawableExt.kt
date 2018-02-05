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

package com.ivianuu.aesthetic.util

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.v4.graphics.drawable.DrawableCompat

internal fun Drawable.tint(color: Int): Drawable {
    mutate()
    DrawableCompat.setTintMode(this, PorterDuff.Mode.SRC_IN)
    DrawableCompat.setTint(this, color)
    return this
}

internal fun Drawable.tint(colorStateList: ColorStateList): Drawable {
    mutate()
    DrawableCompat.setTintList(this, colorStateList)
    return this
}