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
import android.graphics.drawable.Drawable
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.widget.EditText
import android.widget.ImageView
import com.ivianuu.aesthetic.model.BgIconColorState
import com.ivianuu.aesthetic.tinter.AbstractTinter
import com.ivianuu.aesthetic.util.*

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.addTo
import java.lang.reflect.Field
import java.util.concurrent.TimeUnit

internal class ToolbarTinter(view: Toolbar, attrs: AttributeSet) :
    AbstractTinter<Toolbar>(view, attrs) {

    override fun attach() {
        super.attach()

        Observables
            .combineLatest(
                aesthetic.primaryColor(),
                aesthetic.iconTitleColor()
            )
            .map { BgIconColorState(it.first, it.second) }
            .subscribe { invalidateColors(it) }
            .addTo(compositeDisposable)

        // workaround after recreations..
        Observables
            .combineLatest(
                aesthetic.primaryColor(),
                aesthetic.iconTitleColor()
            )
            .map { BgIconColorState(it.first, it.second) }
            .delay(500, TimeUnit.MILLISECONDS)
            .take(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { invalidateColors(it) }
            .addTo(compositeDisposable)
    }

    fun invalidateColors(state: BgIconColorState) {
        val bgColor = state.bgColor
        val activeColor = state.iconTitle.activeColor
        val inactiveColor = state.iconTitle.inactiveColor
        with(view) {

            setBackgroundColor(bgColor)
            setTitleTextColor(activeColor)
            overflowIcon?.tint(activeColor)
            navigationIcon?.tint(activeColor
            )

            // The collapse icon displays when action views are expanded (e.g. SearchView)
            try {
                val field = Toolbar::class.getField("mCollapseIcon")
                (field.get(this) as Drawable?)?.tint(activeColor)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            (0 until menu.size())
                .map { menu.getItem(it) }
                .forEach {
                    it.icon?.tint(activeColor)
                    val actionView = it.actionView
                    if (actionView != null && actionView is SearchView) {
                        with(actionView) {
                            setBackgroundColor(bgColor)

                            val colorStateList = ColorStateLists.getEnabled(activeColor, inactiveColor)

                            try {
                                val searchSrcTextViewField = SearchView::class.getField("mSearchSrcTextView")
                                val searchSrcTextView = searchSrcTextViewField.get(this) as EditText
                                searchSrcTextView.setTextColor(activeColor)
                                searchSrcTextView.setHintTextColor(inactiveColor)
                                searchSrcTextView.setCursorTint(activeColor)

                                var field = SearchView::class.getField("mSearchButton")
                                tintImageView(field, colorStateList)
                                field = SearchView::class.getField("mGoButton")
                                tintImageView(field, colorStateList)
                                field = SearchView::class.getField("mCloseButton")
                                tintImageView(field, colorStateList)
                                field = SearchView::class.getField("mVoiceButton")
                                tintImageView(field, colorStateList)

                                field = SearchView::class.getField("mSearchHintIcon")
                                (field.get(this) as Drawable?)?.tint(colorStateList)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                }
        }
    }
    private fun SearchView.tintImageView(field: Field, colorStateList: ColorStateList) {
        (field.get(this) as ImageView).drawable?.tint(colorStateList)
    }
}