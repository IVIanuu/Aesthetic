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
import android.util.AttributeSet
import android.widget.EditText
import android.widget.ImageView
import com.ivianuu.aesthetic.model.BgIconColorState
import com.ivianuu.aesthetic.tinter.AbstractTinter
import com.ivianuu.aesthetic.util.ColorStateLists
import com.ivianuu.aesthetic.util.getField
import com.ivianuu.aesthetic.util.setCursorTint
import com.ivianuu.aesthetic.util.tint
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.addTo
import java.lang.reflect.Field

internal class SearchViewTinter(view: SearchView, attrs: AttributeSet) :
    AbstractTinter<SearchView>(view, attrs) {

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
    }

    private fun invalidateColors(state: BgIconColorState) {
        val bgColor = state.bgColor
        val activeColor = state.iconTitle.activeColor
        val inactiveColor = state.iconTitle.inactiveColor
        with(view) {
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

    private fun SearchView.tintImageView(field: Field, colorStateList: ColorStateList) {
        (field.get(this) as ImageView).drawable?.tint(colorStateList)
    }
}