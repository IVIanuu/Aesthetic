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

package com.ivianuu.aesthetic

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.LayoutInflaterFactory
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.support.v7.view.ContextThemeWrapper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.ivianuu.aesthetic.tint.util.getField
import java.lang.reflect.Field
import java.lang.reflect.Method

internal class AestheticInflationFactory(
    private val keyContext: AppCompatActivity,
    private val layoutInflater: LayoutInflater,
    private val delegate: AppCompatDelegate?
) : LayoutInflaterFactory {

    private val interceptors = ArrayList<Interceptor>()

    private val onCreateViewMethod: Method
    private val createViewMethod: Method
    private val constructorArgsField: Field
    private var attrsTheme: IntArray? = null

    init {
        try {
            onCreateViewMethod = LayoutInflater::class.java.getDeclaredMethod(
                "onCreateView", View::class.java, String::class.java, AttributeSet::class.java
            )
        } catch (e: NoSuchMethodException) {
            throw IllegalStateException("Failed to retrieve the onCreateView method.", e)
        }

        try {
            createViewMethod = LayoutInflater::class.java.getDeclaredMethod(
                "createView", String::class.java, String::class.java, AttributeSet::class.java
            )
        } catch (e: NoSuchMethodException) {
            throw IllegalStateException("Failed to retrieve the createView method.", e)
        }

        try {
            constructorArgsField = LayoutInflater::class.getField("mConstructorArgs")
        } catch (e: NoSuchFieldException) {
            throw IllegalStateException("Failed to retrieve the mConstructorArgs field.", e)
        }

        try {
            val attrsThemeField = LayoutInflater::class.getField("ATTRS_THEME")
            attrsTheme = attrsThemeField.get(null) as IntArray
        } catch (t: Throwable) {
            t.printStackTrace()
        }

        onCreateViewMethod.isAccessible = true
        createViewMethod.isAccessible = true
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        var view: View?

        // First, check if the AppCompatDelegate will give us a view, usually (maybe always) null.
        if (delegate != null) {
            view = delegate.createView(parent, name, context, attrs)
            view = if (view == null) {
                keyContext.onCreateView(parent, name, context, attrs)
            } else {
                null
            }
        } else {
            view = null
        }

        view = try {
            var viewContext = layoutInflater.context
            // Apply a theme wrapper, if requested.
            if (attrsTheme != null) {
                val ta = viewContext.obtainStyledAttributes(attrs, attrsTheme)
                val themeResId = ta.getResourceId(0, 0)
                if (themeResId != 0) {
                    viewContext = ContextThemeWrapper(viewContext, themeResId)
                }
                ta.recycle()
            }

            val constructorArgs: Array<Any>
            try {
                constructorArgs = constructorArgsField.get(layoutInflater) as Array<Any>
            } catch (e: IllegalAccessException) {
                throw IllegalStateException(
                    "Failed to retrieve the mConstructorArgsField field.", e
                )
            }

            val lastContext = constructorArgs[0]
            constructorArgs[0] = viewContext
            try {
                if (-1 == name.indexOf('.')) {
                    onCreateViewMethod.invoke(
                        layoutInflater,
                        parent,
                        name,
                        attrs
                    ) as View
                } else {
                    createViewMethod.invoke(
                        layoutInflater,
                        name,
                        null,
                        attrs
                    ) as View
                }
            } catch (e: Exception) {
                e.printStackTrace()
                e.printStackTrace()
                null
            } finally {
                constructorArgs[0] = lastContext
            }
        } catch (t: Throwable) {
            throw RuntimeException(
                String.format(
                    "An error occurred while inflating View %s: %s",
                    name,
                    t.message
                ),
                t
            )
        }

        if (view != null) interceptors.forEach { it.onViewInflated(view, attrs) }

        return view
    }

    fun addInterceptor(interceptor: Interceptor) {
        if (!interceptors.contains(interceptor)) {
            interceptors.add(interceptor)
        }
    }

    interface Interceptor {

        fun onViewInflated(view: View, attrs: AttributeSet)

    }
}