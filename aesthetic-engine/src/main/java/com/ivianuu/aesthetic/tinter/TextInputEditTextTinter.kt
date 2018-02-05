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

import android.support.design.widget.TextInputEditText
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.ivianuu.aesthetic.theming.tint
import com.ivianuu.aesthetic.tinter.base.AbstractTinter
import com.ivianuu.aesthetic.util.getObservableForResId
import com.ivianuu.aesthetic.util.resolveResId
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit

internal class TextInputEditTextTinter(view: TextInputEditText, attrs: AttributeSet) :
    AbstractTinter<TextInputEditText>(view, attrs) {

    private val backgroundResId = context.resolveResId(attrs, android.R.attr.background)

    override fun attach() {
        super.attach()

        aesthetic
            .primaryTextColor()
            .subscribe { view.setTextColor(it) }
            .addTo(compositeDisposable)

        aesthetic
            .secondaryTextColor()
            .subscribe { view.setHintTextColor(it) }
            .addTo(compositeDisposable)

        Observables
            .combineLatest(
                context.getObservableForResId(backgroundResId, aesthetic.accentColor()),
                aesthetic.isDark()
            )
            .subscribe { view.tint(it.first, it.second) }
            .addTo(compositeDisposable)

        // workaround because the text input layout clears our background when it animates
        focusChanges()
            .delay(50, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                Observables
                    .combineLatest(
                        context.getObservableForResId(backgroundResId, aesthetic.accentColor()),
                        aesthetic.isDark()
                    )
            }
            .subscribe { view.tint(it.first, it.second) }
            .addTo(compositeDisposable)

    }

    private fun focusChanges() = Observable.create<Unit> { e ->
        val listener = View.OnFocusChangeListener { v, hasFocus ->
            if (!e.isDisposed) {
                e.onNext(Unit)
            }
        }

        e.setCancellable { view.onFocusChangeListener = null }

        view.onFocusChangeListener = listener
    }
}