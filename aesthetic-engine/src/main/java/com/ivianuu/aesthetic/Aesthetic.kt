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

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.LayoutInflaterCompat
import android.support.v7.app.AppCompatActivity
import com.ivianuu.aesthetic.mode.AutoSwitchMode
import com.ivianuu.aesthetic.model.ActiveInactiveColors
import com.ivianuu.aesthetic.theming.util.*
import com.ivianuu.aesthetic.tinter.base.TintBinder
import com.ivianuu.aesthetic.util.MaterialDialogHelper
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.addTo

class Aesthetic private constructor(private val activity: AppCompatActivity) :
    Application.ActivityLifecycleCallbacks {

    private val themeStore = ThemeStore.get(activity)

    private var lastActivityTheme = 0

    private val compositeDisposable = CompositeDisposable()

    init {
        activity.application.registerActivityLifecycleCallbacks(this)

        // set inflation interceptor
        val inflationFactory =
            AestheticInflationFactory(activity).apply { addInterceptor(TintBinder()) }
        LayoutInflaterCompat.setFactory(activity.layoutInflater, inflationFactory)

        // set theme
        activityTheme()
            .take(1)
            .subscribe {
                lastActivityTheme = it
                if (it != 0) {
                    activity.setTheme(it)
                }
            }
            .addTo(compositeDisposable)
    }

    override fun onActivityResumed(activity: Activity?) {
        if (this.activity != activity) return

        // recreate on theme changes
        activityTheme()
            .subscribe {
                if (lastActivityTheme != it) {
                    activity.recreate()
                }
            }
            .addTo(compositeDisposable)

        // set task description
        primaryColor()
            .subscribe { activity.setTaskDescriptionColor(it) }
            .addTo(compositeDisposable)

        // status bar
        Observables
            .combineLatest(
                statusBarColor(),
                lightStatusBarMode()
            )
            .subscribe { (color, mode) ->
                val light = when (mode) {
                    AutoSwitchMode.AUTO -> color.isLight()
                    AutoSwitchMode.ON -> true
                    AutoSwitchMode.OFF -> false
                    else -> throw IllegalArgumentException("unknown mode $mode")
                }

                activity.setStatusBarColorCompat(color)
                activity.setLightStatusBarCompat(light)
            }
            .addTo(compositeDisposable)

        // nav bar
        Observables
            .combineLatest(
                navigationBarColor(),
                lightNavigationBarMode()
            )
            .subscribe { (color, mode) ->
                val light = when (mode) {
                    AutoSwitchMode.AUTO -> color.isLight()
                    AutoSwitchMode.ON -> true
                    AutoSwitchMode.OFF -> false
                    else -> throw IllegalArgumentException("unknown mode $mode")
                }

                activity.setNavBarColorCompat(color)
                activity.setLightNavBarCompat(light)
            }
            .addTo(compositeDisposable)

        // md dialogs
        if (MaterialDialogHelper.shouldSupport()) {
            Observables
                .combineLatest(
                    accentColor(),
                    isDark()
                )
                .subscribe { MaterialDialogHelper.theme(it.first, it.second) }
                .addTo(compositeDisposable)
        }
    }

    override fun onActivityPaused(activity: Activity?) {
        if (this.activity != activity) return
        compositeDisposable.clear()
    }

    override fun onActivityDestroyed(activity: Activity?) {
        if (this.activity == activity) {
            activity.application.unregisterActivityLifecycleCallbacks(this)
            activeAesthetics.remove(activity)
        }
    }

    fun isFirstTime() = themeStore.isFirstTime()

    fun activityTheme() = themeStore.activityTheme()

    fun isDark() = themeStore.isDark()

    fun primaryColor(): Observable<Int> {
        return themeStore.primaryColor()
            .map {
                if (it == 0) {
                    activity.resolveColor(R.attr.colorPrimary)
                } else {
                    it
                }
            }
    }

    fun accentColor(): Observable<Int> {
        return themeStore.accentColor()
            .map {
                if (it == 0) {
                    activity.resolveColor(R.attr.colorAccent)
                } else {
                    it
                }
            }
    }

    fun primaryColorDark(): Observable<Int> {
        return themeStore.primaryColorDark()
            .map {
                if (it == 0) {
                    activity.resolveColor(R.attr.colorPrimaryDark)
                } else {
                    it
                }
            }
    }

    fun primaryTextColor(): Observable<Int> {
        return themeStore.primaryTextColor()
            .map {
                if (it == 0) {
                    activity.resolveColor(android.R.attr.textColorPrimary)
                } else {
                    it
                }
            }
    }

    fun secondaryTextColor(): Observable<Int> {
        return themeStore.secondaryTextColor()
            .map {
                if (it == 0) {
                    activity.resolveColor(android.R.attr.textColorSecondary)
                } else {
                    it
                }
            }
    }

    fun primaryTextInverseColor(): Observable<Int> {
        return themeStore.primaryTextInverseColor()
            .map {
                if (it == 0) {
                    activity.resolveColor(android.R.attr.textColorPrimaryInverse)
                } else {
                    it
                }
            }
    }

    fun secondaryTextInverseColor(): Observable<Int> {
        return themeStore.secondaryTextInverseColor()
            .map {
                if (it == 0) {
                    activity.resolveColor(android.R.attr.textColorSecondaryInverse)
                } else {
                    it
                }
            }
    }

    fun windowBackgroundColor(): Observable<Int> {
        return themeStore.windowBackgroundColor()
            .map {
                if (it == 0) {
                    activity.resolveColor(android.R.attr.windowBackground)
                } else {
                    it
                }
            }
    }

    fun iconTitleColor(background: Observable<Int> = primaryColor()): Observable<ActiveInactiveColors> {
        return background
            .switchMap {
                val dark = it.isDark()

                val active = themeStore.iconTitleActiveColor()
                    .map {
                        if (it == 0) {
                            ContextCompat.getColor(
                                activity,
                                if (dark) {
                                    R.color.md_icon_dark
                                } else {
                                    R.color.md_icon_light
                                }
                            )
                        } else {
                            it
                        }
                    }

                val inactive = themeStore.iconTitleInactiveColor()
                    .map {
                        if (it == 0) {
                            ContextCompat.getColor(
                                activity,
                                if (dark) {
                                    R.color.md_icon_dark_inactive
                                } else {
                                    R.color.md_icon_light_inactive
                                }
                            )
                        } else {
                            it
                        }
                    }

                Observables
                    .combineLatest(active, inactive)
            }
            .map { ActiveInactiveColors(it.first, it.second) }
    }

    fun statusBarColor(): Observable<Int> {
        return themeStore.statusBarColor()
            .switchMap {
                if (it == 0) {
                    primaryColorDark()
                } else {
                    Observable.just(it)
                }
            }
    }

    fun navigationBarColor(): Observable<Int> {
        return themeStore.navigationBarColor()
            .map {
                if (it == 0) {
                    Color.BLACK
                } else {
                    it
                }
            }
    }

    fun lightStatusBarMode() = themeStore.lightStatusBarMode()

    fun lightNavigationBarMode() = themeStore.lightNavigationBarMode()

    fun bottomNavBgMode() = themeStore.bottomNavBgMode()

    fun bottomNavIconTextMode() = themeStore.bottomNavIconTextMode()

    fun cardViewBackgroundColor(): Observable<Int> {
        return themeStore.cardViewBackgroundColor()
            .switchMap {
                if (it == 0) {
                    isDark()
                        .map { activity.getCardColor(it) }
                } else {
                    Observable.just(it)
                }
            }
    }

    fun navigationViewMode() = themeStore.navigationViewMode()

    fun snackbarTextColor(): Observable<Int> {
        return themeStore.snackbarTextColor()
            .map {
                if (it == 0) {
                    activity.getPrimaryTextColor(true)
                } else {
                    it
                }
            }
    }

    fun snackbarActionTextColor(): Observable<Int> {
        return themeStore.snackbarActionTextColor()
            .switchMap {
                if (it == 0) {
                    accentColor()
                } else {
                    Observable.just(it)
                }
            }
    }

    fun tabLayoutBgMode() = themeStore.tabLayoutBgMode()

    fun tabLayoutIndicatorMode() = themeStore.tabLayoutIndicatorMode()

    fun edit() = themeStore.edit()

    // ignore
    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {}
    override fun onActivityStarted(activity: Activity?) {}
    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}
    override fun onActivityStopped(activity: Activity?) {}

    companion object {

        private val activeAesthetics = HashMap<AppCompatActivity, Aesthetic>()

        fun attach(activity: AppCompatActivity): Aesthetic {
            return activeAesthetics.getOrPut(activity) {
                Aesthetic(
                    activity
                )
            }
        }

        fun get(context: Context): Aesthetic {
            val activity = findActivity(context)
            return activeAesthetics[activity]
                    ?: throw IllegalStateException("attach must be called before calling get()")
        }

        private fun findActivity(context: Context): Activity {
            var context = context
            while (context is ContextWrapper) {
                if (context is Activity) {
                    return context
                }
                context = context.baseContext
            }

            throw IllegalStateException("no activity found")
        }
    }
}