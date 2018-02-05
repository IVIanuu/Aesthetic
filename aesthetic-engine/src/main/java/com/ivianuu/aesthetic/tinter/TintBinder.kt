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

import android.support.design.widget.*
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.NestedScrollView
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.*
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.View
import android.widget.*
import com.ivianuu.aesthetic.AestheticInflationFactory
import com.ivianuu.aesthetic.R
import com.ivianuu.aesthetic.widget.*

internal class TintBinder : AestheticInflationFactory.Interceptor {

    override fun onViewInflated(view: View, attrs: AttributeSet) {
        // get tinter
        val tinter: Tinter = when (view) {
            is BottomNavigationView -> BottomNavigationViewTinter(view, attrs)
            is CardView -> CardViewTinter(view, attrs)
            is DrawerLayout -> DrawerLayoutTinter(view, attrs)
            is ImageView -> {
                when (view) {
                    is FloatingActionButton -> FloatingActionButtonTinter(view, attrs)
                    is ImageButton -> ImageButtonTinter(view, attrs)
                    else -> ImageViewTinter(view, attrs)
                }
            }
            is ListView -> ListViewTinter(view, attrs)
            is NavigationView -> NavigationViewTinter(view, attrs)
            is NestedScrollView -> NestedScrollViewTinter(view, attrs)
            is ProgressBar -> {
                when (view) {
                    is SeekBar -> SeekBarTinter(view, attrs)
                    else -> ProgressBarTinter(view, attrs)
                }
            }
            is RecyclerView -> RecyclerViewTinter(view, attrs)
            is ScrollView -> ScrollViewTinter(view, attrs)
            is SearchView -> SearchViewTinter(view, attrs)
            is Spinner -> SpinnerTinter(view, attrs)
            is SwipeRefreshLayout -> SwipeRefreshLayoutTinter(view, attrs)
            is TabLayout -> TabLayoutTinter(view, attrs)
            is TextInputLayout -> TextInputLayoutTinter(view, attrs)
            is TextView -> {
                when (view) {
                    is Button -> {
                        when (view) {
                            is CheckBox -> CheckBoxTinter(view, attrs)
                            is RadioButton -> RadioButtonTinter(view, attrs)
                            is Switch -> SwitchTinter(view, attrs)
                            is SwitchCompat -> SwitchCompatTinter(view, attrs)
                            else -> {
                                if (view.id == R.id.snackbar_action) {
                                    SnackbarButtonTinter(view, attrs)
                                } else {
                                    ButtonTinter(view, attrs)
                                }
                            }
                        }
                    }
                    is EditText -> EditTextTinter(view, attrs)
                    else -> {
                        if (view.id == android.R.id.button1
                            || view.id == android.R.id.button2
                            || view.id == android.R.id.button3) {
                            DialogButtonTinter(view, attrs)
                        } else if (view.id == R.id.snackbar_text) {
                            SnackbarTextViewTinter(view, attrs)
                        } else {
                            TextViewTinter(view, attrs)
                        }
                    }
                }
            }
            is Toolbar -> ToolbarTinter(view, attrs)
            is ViewPager -> ViewPagerTinter(view, attrs)
            else -> ViewBackgroundTinter(view, attrs)
        }

        // attach tinter
        val binder = TintBinding(tinter, view)
        view.addOnAttachStateChangeListener(binder)
    }
}