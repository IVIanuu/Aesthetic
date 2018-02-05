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
import android.support.design.widget.TextInputLayout
import android.util.AttributeSet
import com.ivianuu.aesthetic.tinter.AbstractTinter
import com.ivianuu.aesthetic.util.MaterialColorHelper
import com.ivianuu.aesthetic.util.getField
import com.ivianuu.aesthetic.util.getObservableForResId
import com.ivianuu.aesthetic.util.resolveResId
import io.reactivex.rxkotlin.addTo

internal class TextInputLayoutTinter(view: TextInputLayout, attrs: AttributeSet) :
    AbstractTinter<TextInputLayout>(view, attrs) {

    private val backgroundResId = context.resolveResId(attrs, android.R.attr.background)

    override fun attach() {
        super.attach()

        aesthetic.secondaryTextColor()
            .subscribe { setHintTextColor(it) }
            .addTo(compositeDisposable)

        context.getObservableForResId(backgroundResId, aesthetic.accentColor())
            .subscribe { setAccentTextColor(it) }
            .addTo(compositeDisposable)
    }

    private fun setHintTextColor(hintColor: Int) {
        try {
            val mDefaultTextColorField =
                TextInputLayout::class.getField("mDefaultTextColor")
            mDefaultTextColorField.set(view, ColorStateList.valueOf(hintColor))
            val updateLabelStateMethod = TextInputLayout::class.java.getDeclaredMethod(
                "updateLabelState",
                Boolean::class.javaPrimitiveType,
                Boolean::class.javaPrimitiveType
            )
            updateLabelStateMethod.isAccessible = true
            updateLabelStateMethod.invoke(view, false, true)
        } catch (t: Throwable) {
            throw IllegalStateException(
                "Failed to set TextInputLayout hint (collapsed) color: " + t.localizedMessage, t
            )
        }
    }

    private fun setAccentTextColor(accentColor: Int) {
        try {
            val mFocusedTextColorField =
                TextInputLayout::class.getField("mFocusedTextColor")
            mFocusedTextColorField.set(view, ColorStateList.valueOf(accentColor))
            val updateLabelStateMethod = TextInputLayout::class.java.getDeclaredMethod(
                "updateLabelState",
                Boolean::class.javaPrimitiveType,
                Boolean::class.javaPrimitiveType
            )
            updateLabelStateMethod.isAccessible = true
            updateLabelStateMethod.invoke(view, false, true)
        } catch (t: Throwable) {
            throw IllegalStateException(
                "Failed to set TextInputLayout accent (expanded) color: " + t.localizedMessage, t
            )
        }

    }
}
