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

import android.graphics.drawable.Drawable
import android.support.v7.view.menu.ActionMenuItemView
import android.util.AttributeSet
import com.ivianuu.aesthetic.model.ActiveInactiveColors
import com.ivianuu.aesthetic.tinter.AbstractTinter
import com.ivianuu.aesthetic.util.getField
import com.ivianuu.aesthetic.util.tint
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.addTo

internal class ActionMenuItemViewTinter(view: ActionMenuItemView, attrs: AttributeSet) :
    AbstractTinter<ActionMenuItemView>(view, attrs) {

    override fun attach() {
        super.attach()

        aesthetic
            .iconTitleColor()
            .subscribe { invalidateColors(it) }
            .addTo(compositeDisposable)
    }

    private fun invalidateColors(colors: ActiveInactiveColors) {
        val iconField = ActionMenuItemView::class.getField("mIcon")
        val icon = iconField.get(view) as Drawable?
        icon?.tint(colors.toEnabledS1())
        view.setTextColor(colors.toEnabledS1())
    }

}