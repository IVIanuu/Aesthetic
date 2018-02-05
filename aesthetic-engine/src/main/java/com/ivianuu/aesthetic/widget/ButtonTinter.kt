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
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.support.design.R.style.Widget_AppCompat_Button_Borderless
import android.support.design.R.style.Widget_AppCompat_Button_Borderless_Colored
import android.util.AttributeSet
import android.widget.Button
import com.ivianuu.aesthetic.tinter.AbstractTinter
import com.ivianuu.aesthetic.util.*
import io.reactivex.rxkotlin.addTo


internal class ButtonTinter(
    view: Button,
    attrs: AttributeSet
) : AbstractTinter<Button>(view, attrs) {

    private val backgroundResId = context.resolveResId(attrs, android.R.attr.background)
    private val styleResId = attrs.styleAttribute
    private val borderless =
        styleResId == Widget_AppCompat_Button_Borderless_Colored
                || styleResId == Widget_AppCompat_Button_Borderless

    override fun attach() {
        super.attach()

        if (!borderless) {
            context.getObservableForResId(backgroundResId, aesthetic.accentColor())
                .subscribe { invalidateColors(it) }
                .addTo(compositeDisposable)
        } else if (styleResId == Widget_AppCompat_Button_Borderless_Colored) {
            aesthetic
                .accentColor()
                .subscribe {
                    val disabledColor = it.adjustAlpha(0.70f)
                    view.setTextColor(getEnabledColorStateList(it, disabledColor))
                }
                .addTo(compositeDisposable)
        }
    }

    private fun invalidateColors(color: Int) {
        val disabled = MaterialColorHelper.getButtonDisabledColor(context)
        val rippleColor = MaterialColorHelper.getRippleColor(context, color)
        val textColor = MaterialColorHelper.getPrimaryTextColor(context, color)
        val colorStateList = getDisabledColorStateList(color, disabled)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && view.background is RippleDrawable) {
            val rd = view.background as RippleDrawable
            rd.setColor(ColorStateList.valueOf(rippleColor))
        }

        view.setTextColor(
            getDisabledColorStateList(
                textColor,
                MaterialColorHelper.getPrimaryTextColor(context)
            )
        )

        view.background?.tint(colorStateList)

        val textColorSl = ColorStateList(
            arrayOf(
                intArrayOf(R.attr.state_enabled),
                intArrayOf(-R.attr.state_enabled)
            ),
            intArrayOf(
                if (color.isLight()) Color.BLACK else Color.WHITE,
                if (MaterialColorHelper.isDarkTheme(context)) Color.WHITE else Color.BLACK
            )
        )

        with(view) {
            setTextColor(textColorSl)
            // Hack around button color not updating
            isEnabled = !isEnabled
            isEnabled = !isEnabled
        }
    }
}