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
import android.os.Build
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.widget.CheckBox
import com.ivianuu.aesthetic.tinter.AbstractTinter
import com.ivianuu.aesthetic.util.MaterialColorHelper
import com.ivianuu.aesthetic.util.getObservableForResId
import com.ivianuu.aesthetic.util.resolveResId
import com.ivianuu.aesthetic.util.tint
import io.reactivex.rxkotlin.addTo

internal class CheckBoxTinter(view: CheckBox, attrs: AttributeSet) :
    AbstractTinter<CheckBox>(view, attrs) {

    private val backgroundResId = context.resolveResId(attrs, android.R.attr.background)

    override fun attach() {
        super.attach()

        context.getObservableForResId(backgroundResId, aesthetic.accentColor())
            .subscribe { invalidateColors(it) }
            .addTo(compositeDisposable)
    }

    private fun invalidateColors(color: Int) {
        val sl = ColorStateList(
            arrayOf(
                intArrayOf(-R.attr.state_enabled),
                intArrayOf(R.attr.state_enabled, -R.attr.state_checked),
                intArrayOf(R.attr.state_enabled, R.attr.state_checked)
            ),
            intArrayOf(
                MaterialColorHelper.getControlDisabledColor(context),
                MaterialColorHelper.getControlNormalColor(context),
                color
            )
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.buttonTintList = sl
        } else {
            view.buttonDrawable =
                    ContextCompat.getDrawable(context, com.ivianuu.aesthetic.R.drawable.abc_btn_check_material)?.tint(color)
        }
    }
}