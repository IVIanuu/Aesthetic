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

package com.ivianuu.aesthetic.tinter

import android.annotation.SuppressLint
import android.util.AttributeSet
import android.util.Log
import android.widget.EditText
import com.ivianuu.aesthetic.theming.tint
import com.ivianuu.aesthetic.tinter.base.AbstractTinter
import com.ivianuu.aesthetic.util.getObservableForResId
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.addTo

@SuppressLint("ResourceType")
internal class EditTextTinter(view: EditText, attrs: AttributeSet) :
    AbstractTinter<EditText>(view, attrs) {

    private val backgroundResId: Int
    private val textColorResId: Int
    private val textColorHintResId: Int

    init {
        val attrsArray = intArrayOf(
            android.R.attr.background,
            android.R.attr.textColor,
            android.R.attr.textColorHint
        )
        val ta = context.obtainStyledAttributes(attrs, attrsArray)
        backgroundResId = ta.getResourceId(0, 0)
        textColorResId = ta.getResourceId(1, 0)
        textColorHintResId = ta.getResourceId(2, 0)
        ta.recycle()
    }

    override fun attach() {
        super.attach()

        Observables
            .combineLatest(
                context.getObservableForResId(backgroundResId, aesthetic.accentColor()),
                aesthetic.isDark()
            )
            .subscribe {
                view.tint(it.first, it.second)
            }
            .addTo(compositeDisposable)

        context
            .getObservableForResId(textColorResId)
            .subscribe { view.setTextColor(it) }
            .addTo(compositeDisposable)

        context
            .getObservableForResId(textColorHintResId)
            .subscribe { view.setHintTextColor(it) }
            .addTo(compositeDisposable)
    }
}