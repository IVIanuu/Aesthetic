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

import android.content.Context
import android.support.annotation.AttrRes
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import com.ivianuu.aesthetic.Aesthetic
import com.ivianuu.aesthetic.R
import io.reactivex.Observable

internal fun Context.getResColor(resId: Int) = ContextCompat.getColor(this, resId)

internal fun Context.resolveColor(
    attr: Int,
    fallback: Int = 0
): Int {
    val a = theme.obtainStyledAttributes(intArrayOf(attr))
    return try {
        a.getColor(0, fallback)
    } catch (ignored: Throwable) {
        fallback
    } finally {
        a.recycle()
    }
}

internal fun Context.resolveResId(@AttrRes attr: Int, fallback: Int = 0): Int {
    val a = theme.obtainStyledAttributes(intArrayOf(attr))
    try {
        return a.getResourceId(0, fallback)
    } finally {
        a.recycle()
    }
}

internal fun Context.resolveResId(attrs: AttributeSet, @AttrRes attrId: Int): Int {
    val ta = obtainStyledAttributes(attrs, intArrayOf(attrId))
    val result = ta.getResourceId(0, 0)
    ta.recycle()
    return result
}

internal fun Context.getObservableForResId(
    resId: Int, fallback: Observable<Int> = Observable.never()
): Observable<Int> {
    return when (resId) {
        resolveResId(R.attr.colorPrimary) -> Aesthetic.get(this).primaryColor()
        resolveResId(R.attr.colorPrimaryDark) -> Aesthetic.get(this).primaryColorDark()
        resolveResId(R.attr.colorAccent) -> Aesthetic.get(this).accentColor()
        resolveResId(android.R.attr.statusBarColor) -> Aesthetic.get(this).statusBarColor()
        resolveResId(android.R.attr.windowBackground) -> Aesthetic.get(this).windowBackgroundColor()
        resolveResId(android.R.attr.textColorPrimary) -> Aesthetic.get(this).primaryTextColor()
        resolveResId(android.R.attr.textColorSecondary) -> Aesthetic.get(this).secondaryTextColor()
        resolveResId(android.R.attr.textColorPrimaryInverse) -> Aesthetic.get(this).primaryTextInverseColor()
        resolveResId(android.R.attr.textColorSecondaryInverse) -> Aesthetic.get(this).secondaryTextInverseColor()
        else -> fallback
    }
}