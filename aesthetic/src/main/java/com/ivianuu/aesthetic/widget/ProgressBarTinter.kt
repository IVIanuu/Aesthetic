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
import android.graphics.PorterDuff
import android.os.Build
import android.util.AttributeSet
import android.widget.ProgressBar
import com.ivianuu.aesthetic.tinter.AbstractTinter

import io.reactivex.rxkotlin.addTo

internal class ProgressBarTinter(
    view: ProgressBar,
    attrs: AttributeSet
) : AbstractTinter<ProgressBar>(view, attrs) {

    override fun attach() {
        super.attach()

        aesthetic
            .accentColor()
            .subscribe { invalidateColors(it) }
            .addTo(compositeDisposable)
    }

    private fun invalidateColors(color: Int) {
        with(view) {
            val sl = ColorStateList.valueOf(color)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                progressTintList = sl
                secondaryProgressTintList = sl
                indeterminateTintList = sl
            } else {
                val mode = PorterDuff.Mode.SRC_IN
                indeterminateDrawable?.setColorFilter(color, mode)
                progressDrawable?.setColorFilter(color, mode)
            }
        }
    }
}