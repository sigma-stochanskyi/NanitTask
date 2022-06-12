package com.stochanskyi.nanittask.presentation.utils.insets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.internal.ViewUtils.requestApplyInsetsWhenAttached
import com.stochanskyi.nanittask.presentation.utils.onChildAddedListener

open class InsetAwareNavigationContainerFragment : NavHostFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState).also {
            (it as ViewGroup).setupInsets()
        }
    }

    private fun ViewGroup.setupInsets() {
        onChildAddedListener { parent, child ->
            child.onApplyDispatchInsetsWithPaddings()
        }
        requestApplyInsetsWhenAttached(this)
    }
}