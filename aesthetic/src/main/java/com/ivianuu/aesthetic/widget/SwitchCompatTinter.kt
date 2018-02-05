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

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.support.v7.widget.SwitchCompat
import android.util.AttributeSet
import com.ivianuu.aesthetic.model.ColorIsDarkState

import com.ivianuu.aesthetic.tinter.AbstractTinter
import com.ivianuu.aesthetic.util.*
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.addTo

internal class SwitchCompatTinter(view: SwitchCompat, attrs: AttributeSet) :
    AbstractTinter<SwitchCompat>(view, attrs) {

    private val backgroundResId = context.resolveResId(attrs, android.R.attr.background)

    override fun attach() {
        super.attach()

        Observables
            .combineLatest(
                context.getObservableForResId(backgroundResId, aesthetic.accentColor()),
                aesthetic.isDark()
            )
            .map { ColorIsDarkState(it.first, it.second) }
            .subscribe { invalidateColors(it) }
            .addTo(compositeDisposable)
    }

    private fun invalidateColors(state: ColorIsDarkState) {
        with(view) {
            if (trackDrawable != null) {
                trackDrawable = modifySwitchDrawable(
                    context,
                    trackDrawable,
                    state.color,
                    false,
                    state.isDark
                )
            }
            if (thumbDrawable != null) {
                thumbDrawable = modifySwitchDrawable(
                    context,
                    thumbDrawable,
                    state.color,
                    true,
                    state.isDark
                )
            }
        }
    }

    private fun modifySwitchDrawable(
        context: Context,
        from: Drawable,
        tint: Int,
        thumb: Boolean,
        dark: Boolean
    ): Drawable? {
        var tint = tint
        if (tint.isDark()) {
            tint = tint.lighten()
        }
        tint = tint.adjustAlpha(if (!thumb) 0.5f else 1.0f)
        val disabled: Int
        val normal: Int
        if (thumb) {
            disabled = MaterialColorHelper.getSwitchThumbDisabledColor(context, dark)
            normal = MaterialColorHelper.getSwitchThumbNormalColor(context, dark)
        } else {
            disabled = MaterialColorHelper.getSwitchTrackDisabledColor(context, dark)
            normal = MaterialColorHelper.getSwitchTrackNormalColor(context, dark)
        }

        val sl = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_enabled),
                intArrayOf(
                    android.R.attr.state_enabled,
                    -android.R.attr.state_activated,
                    -android.R.attr.state_checked
                ),
                intArrayOf(android.R.attr.state_enabled, android.R.attr.state_activated),
                intArrayOf(android.R.attr.state_enabled, android.R.attr.state_checked)
            ),
            intArrayOf(disabled, normal, tint, tint)
        )
        return from.tint(sl)
    }
}