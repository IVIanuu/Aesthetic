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

package com.ivianuu.aesthetic.util

import android.content.res.ColorStateList
import com.ivianuu.aesthetic.Aesthetic
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Observables

internal object MaterialDialogHelper {

    fun shouldSupport(): Boolean {
        try {
            Class.forName("com.afollestad.materialdialogs.internal.ThemeSingleton")
        } catch (e: ClassNotFoundException) {
            return false
        }

        return true
    }

    fun observe(aesthetic: Aesthetic): Disposable {
        return Observables
            .combineLatest(
                aesthetic.accentColor(),
                aesthetic.primaryTextColor(),
                aesthetic.secondaryTextColor(),
                aesthetic.isDark(),
                { accentColor, primaryTextColor, secondaryTextColor, isDark ->
                    Params(accentColor, primaryTextColor, secondaryTextColor, isDark)
                }
            )
            .subscribe { theme(it) }
    }

    private fun theme(params: Params) {
        try {
            val cls = Class.forName("com.afollestad.materialdialogs.internal.ThemeSingleton")
            val getMethod = cls.getMethod("get")
            val instance = getMethod.invoke(null)

            val fieldDarkTheme = cls.getField("darkTheme")
            fieldDarkTheme.set(instance, params.isDark)

            val fieldTitleColor = cls.getField("titleColor")
            fieldTitleColor.set(instance, params.primaryTextColor)

            val fieldContentColor = cls.getField("contentColor")
            fieldContentColor.set(instance, params.secondaryTextColor)

            val fieldItemColor = cls.getField("itemColor")
            fieldItemColor.set(instance, params.secondaryTextColor)

            val fieldPosColor = cls.getField("positiveColor")
            fieldPosColor.set(instance, ColorStateList.valueOf(params.accentColor))

            val fieldNeuColor = cls.getField("neutralColor")
            fieldNeuColor.set(instance, ColorStateList.valueOf(params.accentColor))

            val fieldNegColor = cls.getField("negativeColor")
            fieldNegColor.set(instance, ColorStateList.valueOf(params.accentColor))

            val fieldWidgetColor = cls.getField("widgetColor")
            fieldWidgetColor.set(instance, params.accentColor)

            val fieldLinkColor = cls.getField("linkColor")
            fieldLinkColor.set(instance, ColorStateList.valueOf(params.accentColor))
        } catch (t: Throwable) {
            t.printStackTrace()
        }

    }

    private data class Params(
        val accentColor: Int,
        val primaryTextColor: Int,
        val secondaryTextColor: Int,
        val isDark: Boolean
    )
}
