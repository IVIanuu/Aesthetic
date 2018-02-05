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
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.StateListDrawable
import android.support.design.widget.NavigationView
import android.util.AttributeSet
import com.ivianuu.aesthetic.mode.NavigationViewMode
import com.ivianuu.aesthetic.model.ColorIsDarkState
import com.ivianuu.aesthetic.tinter.AbstractTinter
import com.ivianuu.aesthetic.util.MaterialColorHelper
import com.ivianuu.aesthetic.util.adjustAlpha
import io.reactivex.Observable
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
            .map { ColorIsDarkState(it.first, it.second) }
            .subscribe { invalidateColors(it) }
            .addTo(compositeDisposable)
    }

    private fun invalidateColors(state: ColorIsDarkState) {
        with(view) {
            val baseColor = MaterialColorHelper.getPrimaryTextColor(context, state.isDark)
            val unselectedIconColor = baseColor.adjustAlpha(0.54f)
            val unselectedTextColor = baseColor.adjustAlpha(0.87f)
            val selectedItemBgColor = MaterialColorHelper.getNavigationDrawerSelectedColor(context, state.isDark)

            val iconSl = ColorStateList(
                arrayOf(
                    intArrayOf(-android.R.attr.state_checked),
                    intArrayOf(android.R.attr.state_checked)
                ),
                intArrayOf(unselectedIconColor, state.color)
            )
            val textSl = ColorStateList(
                arrayOf(
                    intArrayOf(-android.R.attr.state_checked),
                    intArrayOf(android.R.attr.state_checked)
                ),
                intArrayOf(unselectedTextColor, state.color)
            )
            itemTextColor = textSl
            itemIconTintList = iconSl

            val bgDrawable = StateListDrawable()
            bgDrawable.addState(
                intArrayOf(android.R.attr.state_checked), ColorDrawable(selectedItemBgColor)
            )

            itemBackground = bgDrawable
        }
    }
}