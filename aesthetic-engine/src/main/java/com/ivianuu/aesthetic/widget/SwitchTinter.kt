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

package com.ivianuu.aesthetic.widget

import android.R
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.Switch

import com.ivianuu.aesthetic.tinter.AbstractTinter
import com.ivianuu.aesthetic.util.*
import io.reactivex.rxkotlin.addTo

internal class SwitchTinter(view: Switch, attrs: AttributeSet) :
    AbstractTinter<Switch>(view, attrs) {

    private val backgroundResId = context.resolveResId(attrs, android.R.attr.background)

    override fun attach() {
        super.attach()

        context.getObservableForResId(backgroundResId, aesthetic.accentColor())
            .subscribe { invalidateColors(it) }
            .addTo(compositeDisposable)
    }

    private fun invalidateColors(color: Int) {
        with(view) {
            if (trackDrawable != null) {
                trackDrawable = modifySwitchDrawable(
                    context,
                    trackDrawable,
                    color,
                    false
                )
            }
            if (thumbDrawable != null) {
                thumbDrawable = modifySwitchDrawable(
                    context,
                    thumbDrawable,
                    color,
                    true
                )
            }
        }
    }

    private fun modifySwitchDrawable(
        context: Context,
        from: Drawable,
        tint: Int,
        thumb: Boolean
    ): Drawable? {
        var tint: Int = tint
        if (tint.isDark()) {
            tint = tint.lighten()
        }
        tint = tint.adjustAlpha(if (!thumb) 0.5f else 1.0f)
        val disabled: Int
        val normal: Int
        if (thumb) {
            disabled = MaterialColorHelper.getSwitchThumbDisabledColor(context)
            normal = MaterialColorHelper.getSwitchThumbNormalColor(context)
        } else {
            disabled = MaterialColorHelper.getSwitchTrackDisabledColor(context)
            normal = MaterialColorHelper.getSwitchTrackNormalColor(context)
        }

        val sl = ColorStateList(
            arrayOf(
                intArrayOf(-R.attr.state_enabled),
                intArrayOf(
                    R.attr.state_enabled,
                    -R.attr.state_activated,
                    -R.attr.state_checked
                ),
                intArrayOf(R.attr.state_enabled, R.attr.state_activated),
                intArrayOf(R.attr.state_enabled, R.attr.state_checked)
            ),
            intArrayOf(disabled, normal, tint, tint)
        )
        return from.tint(sl)
    }
}