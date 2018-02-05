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
import android.content.res.ColorStateList
import android.graphics.Color
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import com.ivianuu.aesthetic.tinter.AbstractTinter
import com.ivianuu.aesthetic.util.*
import io.reactivex.rxkotlin.addTo

internal class FloatingActionButtonTinter(
    view: FloatingActionButton,
    attrs: AttributeSet
) : AbstractTinter<FloatingActionButton>(view, attrs) {

    private val backgroundResId = context.resolveResId(attrs, android.R.attr.background)

    override fun attach() {
        super.attach()

        context.getObservableForResId(backgroundResId, aesthetic.accentColor())
            .subscribe { invalidateColors(it) }
            .addTo(compositeDisposable)
    }

    private fun invalidateColors(color: Int) {
        val dark = color.isDark()
        val pressed = color.lighten()
        val rippleColor = MaterialColorHelper.getRippleColor(context, dark)
        val textColor = MaterialColorHelper.getPrimaryTextColor(context, dark)

        val colorStateList: ColorStateList

        colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(-R.attr.state_pressed),
                intArrayOf(R.attr.state_pressed)
            ),
            intArrayOf(color, pressed)
        )

        view.rippleColor = rippleColor
        view.backgroundTintList = colorStateList
        view.drawable?.tint(textColor)
        view.setColorFilter(if (dark) Color.WHITE else Color.BLACK)
    }
}