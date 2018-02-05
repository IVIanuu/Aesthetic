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

import android.content.Context
import android.support.annotation.CallSuper
import android.util.AttributeSet
import android.view.View
import com.ivianuu.aesthetic.Aesthetic
import io.reactivex.disposables.CompositeDisposable

internal abstract class AbstractTinter<out V : View>(
    protected val view: V,
    protected val attrs: AttributeSet
) : Tinter {

    protected val compositeDisposable = CompositeDisposable()
    protected val context: Context
        get() = view.context
    protected val aesthetic = Aesthetic.get(view.context)

    @CallSuper
    override fun attach() {

    }

    @CallSuper
    override fun detach() {
        compositeDisposable.clear()
    }

}