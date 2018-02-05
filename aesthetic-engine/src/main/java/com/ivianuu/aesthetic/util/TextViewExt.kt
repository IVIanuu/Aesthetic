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

import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.widget.TextView

fun TextView.setCursorTint(color: Int) {
    try {
        val fCursorDrawableRes = TextView::class.getField("mCursorDrawableRes")
        val mCursorDrawableRes = fCursorDrawableRes.getInt(this)
        val fEditor = TextView::class.getField("mEditor")
        val editor = fEditor.get(this)
        val fCursorDrawable = editor::class.getField("mCursorDrawable")
        val drawables = arrayOfNulls<Drawable>(2)
        drawables[0] = ContextCompat.getDrawable(context, mCursorDrawableRes)?.tint(color)
        drawables[1] = ContextCompat.getDrawable(context, mCursorDrawableRes)?.tint(color)
        fCursorDrawable.set(editor, drawables)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun TextView.setTextHandleTint(color: Int) {
    try {
        val editorField = TextView::class.getField("mEditor")
        val editor = editorField.get(this)
        val editorClass = editor::class

        val handleNames = arrayOf(
            "mSelectHandleLeft", "mSelectHandleRight",
            "mSelectHandleCenter"
        )
        val resNames = arrayOf(
            "mTextSelectHandleLeftRes", "mTextSelectHandleRightRes",
            "mTextSelectHandleRes"
        )

        for (i in handleNames.indices) {
            val handleField = editorClass.getField(handleNames[i])

            var handleDrawable: Drawable? = handleField.get(editor) as Drawable?

            if (handleDrawable == null) {
                val resField = TextView::class.getField(resNames[i])
                val resId = resField.getInt(this)
                handleDrawable = ContextCompat.getDrawable(context, resId)
            }

            if (handleDrawable != null) {
                val drawable = handleDrawable.mutate()
                drawable.tint(color)
                handleField.set(editor, drawable)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}