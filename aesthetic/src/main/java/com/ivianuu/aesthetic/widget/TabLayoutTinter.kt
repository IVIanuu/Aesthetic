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

import android.support.design.widget.TabLayout
import android.util.AttributeSet
import android.view.View
import com.ivianuu.aesthetic.mode.TabLayoutIndicatorMode
import com.ivianuu.aesthetic.model.BgIconColorState
import com.ivianuu.aesthetic.tinter.AbstractTinter
import com.ivianuu.aesthetic.util.RippleDrawableHelper
import com.ivianuu.aesthetic.util.getField
import com.ivianuu.aesthetic.util.isDark
import com.ivianuu.aesthetic.util.tint
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.addTo

internal class TabLayoutTinter(view: TabLayout, attrs: AttributeSet) :
    AbstractTinter<TabLayout>(view, attrs) {

    override fun attach() {
        super.attach()

        aesthetic.tabLayoutIndicatorMode()
            .switchMap {
                when (it) {
                    TabLayoutIndicatorMode.PRIMARY -> aesthetic.primaryColor()
                    TabLayoutIndicatorMode.ACCENT -> aesthetic.accentColor()
                    else -> throw IllegalStateException("Unknown indicator mode $it")
                }
            }
            .subscribe { view.setSelectedTabIndicatorColor(it) }
            .addTo(compositeDisposable)

        aesthetic.tabLayoutBgMode()
            .switchMap {
                when (it) {
                    TabLayoutIndicatorMode.PRIMARY -> aesthetic.primaryColor()
                    TabLayoutIndicatorMode.ACCENT -> aesthetic.accentColor()
                    else -> throw IllegalStateException("Unknown bg mode $it")
                }
            }
            .switchMap {
                Observables
                    .combineLatest(
                        Observable.just(it),
                        aesthetic.iconTitleColor(Observable.just(it))
                    )
            }
            .map { BgIconColorState(it.first, it.second) }
            .subscribe { invalidateColors(it) }
            .addTo(compositeDisposable)
    }

    private fun invalidateColors(state: BgIconColorState) {
        with(view) {
            setBackgroundColor(state.bgColor)

            (0 until tabCount).mapNotNull { getTabAt(it) }
                .forEach {
                    it.icon?.tint(state.iconTitle.toDisabledS1())
                    try {
                        val viewField = it::class.getField("mView")
                        val tabView = viewField.get(it) as View
                        tabView.background =
                                RippleDrawableHelper
                                    .getRippleDrawable(context, state.bgColor.isDark())
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

            setTabTextColors(state.iconTitle.inactiveColor, state.iconTitle.activeColor)
        }
    }
}