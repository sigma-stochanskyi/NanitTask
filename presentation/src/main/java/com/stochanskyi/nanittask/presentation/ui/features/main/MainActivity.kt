package com.stochanskyi.nanittask.presentation.ui.features.main

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.stochanskyi.nanittask.presentation.R
import com.stochanskyi.nanittask.presentation.utils.insets.dispatchInsetsToChildren
import com.stochanskyi.nanittask.presentation.utils.insets.getSystemInsets

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initInsets()
    }

    private fun initInsets() {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val content = findViewById<ViewGroup>(android.R.id.content)
        ViewCompat.setOnApplyWindowInsetsListener(content) { _, windowInsets ->
            val navigationInset = windowInsets.getSystemInsets().bottom

            content.updateContentPadding(navigationInset)

            val insetsWithoutBottom = windowInsets.inset(0, 0, 0, navigationInset)

            content.dispatchInsetsToChildren(insetsWithoutBottom)
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun View.updateContentPadding(bottom: Int) {
        setPadding(
            paddingLeft,
            paddingTop,
            paddingEnd,
            bottom
        )
    }
}