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

import android.support.design.widget.TextInputLayout
import android.util.AttributeSet
import com.ivianuu.aesthetic.tint.tint
import com.ivianuu.aesthetic.tinter.AbstractTinter
import com.ivianuu.aesthetic.util.getObservableForResId
import com.ivianuu.aesthetic.util.resolveResId
import io.reactivex.rxkotlin.addTo

internal class TextInputLayoutTinter(view: TextInputLayout, attrs: AttributeSet) :
    AbstractTinter<TextInputLayout>(view, attrs) {

    private val backgroundResId = context.resolveResId(attrs, android.R.attr.background)

    override fun attach() {
        super.attach()

        context.getObservableForResId(backgroundResId, aesthetic.accentColor())
            .subscribe { view.tint(it) }
            .addTo(compositeDisposable)
    }
}
