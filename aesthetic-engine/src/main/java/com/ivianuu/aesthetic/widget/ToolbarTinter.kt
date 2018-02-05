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

import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import com.ivianuu.aesthetic.tint.tint
import com.ivianuu.aesthetic.tinter.AbstractTinter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit

internal class ToolbarTinter(view: Toolbar, attrs: AttributeSet) :
    AbstractTinter<Toolbar>(view, attrs) {

    override fun attach() {
        super.attach()

        aesthetic.primaryColor()
            .subscribe { view.tint(it) }
            .addTo(compositeDisposable)

        // workaround after recreations..
        aesthetic.primaryColor()
            .delay(100, TimeUnit.MILLISECONDS)
            .take(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { view.tint(it) }
            .addTo(compositeDisposable)
    }
}