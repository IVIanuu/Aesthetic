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

import android.content.res.ColorStateList
import android.os.Build
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.widget.RadioButton
import com.ivianuu.aesthetic.R
import com.ivianuu.aesthetic.model.ColorIsDarkState

import com.ivianuu.aesthetic.tinter.AbstractTinter
import com.ivianuu.aesthetic.util.*
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.addTo

internal class RadioButtonTinter(view: RadioButton, attrs: AttributeSet) :
    AbstractTinter<RadioButton>(view, attrs) {

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

        aesthetic
            .primaryTextColor()
            .subscribe { view.setTextColor(it) }
            .addTo(compositeDisposable)
    }

    private fun invalidateColors(state: ColorIsDarkState) {
        val sl = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_enabled),
                intArrayOf(android.R.attr.state_enabled, -android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_enabled, android.R.attr.state_checked)
            ),
            intArrayOf(
                MaterialColorHelper.getControlDisabledColor(context, state.isDark).stripAlpha(),
                MaterialColorHelper.getControlNormalColor(context, state.isDark),
                state.color
            )
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.buttonTintList = sl
        } else {
            view.buttonDrawable = ContextCompat
                .getDrawable(context, R.drawable.abc_btn_radio_material)?.tint(sl)
        }
    }
}