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

import android.graphics.Color
import android.support.design.widget.BottomNavigationView
import android.util.AttributeSet
import com.ivianuu.aesthetic.mode.BottomNavBgMode
import com.ivianuu.aesthetic.mode.BottomNavIconTextMode
import com.ivianuu.aesthetic.theming.tint
import com.ivianuu.aesthetic.theming.util.getBottomNavBgColor
import com.ivianuu.aesthetic.theming.util.isLight
import com.ivianuu.aesthetic.tinter.base.AbstractTinter
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.addTo

internal class BottomNavigationViewTinter(view: BottomNavigationView, attrs: AttributeSet) :
    AbstractTinter<BottomNavigationView>(view, attrs) {

    override fun attach() {
        super.attach()

        Observables
            .combineLatest(
                aesthetic.bottomNavBgMode(),
                aesthetic.bottomNavIconTextMode(),
                aesthetic.isDark()
            )
            .switchMap { (bgMode, iconTextMode, isDark) ->
                val iconTextModeObservable = when (iconTextMode) {
                    BottomNavIconTextMode.SELECTED_PRIMARY -> aesthetic.primaryColor()
                    BottomNavIconTextMode.SELECTED_ACCENT -> aesthetic.accentColor()
                    BottomNavIconTextMode.BLACK_WHITE_AUTO -> Observable.just(Color.TRANSPARENT)
                    else -> throw IllegalStateException("Unknown bottom nav icon/text mode $iconTextMode")
                }

                val bgModeObservable = when (bgMode) {
                    BottomNavBgMode.PRIMARY -> aesthetic.primaryColor()
                    BottomNavBgMode.PRIMARY_DARK -> aesthetic.primaryColorDark()
                    BottomNavBgMode.ACCENT -> aesthetic.accentColor()
                    BottomNavBgMode.BLACK_WHITE_AUTO -> {
                        val color = context.getBottomNavBgColor(isDark)
                        Observable.just(color)
                    }
                    else -> throw IllegalStateException("Unknown bottom nav bg mode $bgMode")
                }

                Observables.combineLatest(iconTextModeObservable, bgModeObservable)
            }
            .subscribe { (iconText, bg) ->
                val iconText = if (iconText == Color.TRANSPARENT) {
                    if (bg.isLight()) Color.BLACK else Color.WHITE
                } else {
                    iconText
                }
                view.tint(bg, iconText)
            }
            .addTo(compositeDisposable)
    }
}