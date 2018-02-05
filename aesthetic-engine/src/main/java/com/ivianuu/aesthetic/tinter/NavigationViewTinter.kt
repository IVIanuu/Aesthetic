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

import android.support.design.widget.NavigationView
import android.util.AttributeSet
import com.ivianuu.aesthetic.mode.NavigationViewMode
import com.ivianuu.aesthetic.theming.tint
import com.ivianuu.aesthetic.tinter.base.AbstractTinter
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.addTo

internal class NavigationViewTinter(view: NavigationView, attrs: AttributeSet) :
    AbstractTinter<NavigationView>(view, attrs) {

    override fun attach() {
        super.attach()

        aesthetic
            .navigationViewMode()
            .switchMap {
                val color = when (it) {
                    NavigationViewMode.SELECTED_PRIMARY -> aesthetic.primaryColor()
                    NavigationViewMode.SELECTED_ACCENT -> aesthetic.accentColor()
                    else -> throw IllegalStateException("Unknown nav view mode $it")
                }

                Observables
                    .combineLatest(
                        color,
                        aesthetic.isDark()
                    )
            }
            .subscribe { view.tint(it.first, it.second) }
            .addTo(compositeDisposable)
    }

}