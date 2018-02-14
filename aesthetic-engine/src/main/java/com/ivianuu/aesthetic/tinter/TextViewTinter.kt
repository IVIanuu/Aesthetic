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

import android.util.AttributeSet
import android.widget.TextView
import com.ivianuu.aesthetic.tinter.base.AbstractTinter
import com.ivianuu.aesthetic.util.getObservableForResId
import com.ivianuu.aesthetic.util.resolveResId
import io.reactivex.rxkotlin.addTo

internal class TextViewTinter(view: TextView, attrs: AttributeSet) :
    AbstractTinter<TextView>(view, attrs) {

    private val textColorResId = context.resolveResId(attrs, android.R.attr.textColor)

    override fun attach() {
        super.attach()

        val fallback = if (view.id == android.R.id.title) {
            aesthetic.primaryTextColor()
        } else {
            aesthetic.secondaryTextColor()
        }

        context
            .getObservableForResId(textColorResId, fallback)
            .subscribe { view.setTextColor(it) }
            .addTo(compositeDisposable)
    }

}