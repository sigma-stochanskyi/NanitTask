package com.stochanskyi.nanittask.presentation.utils

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams

fun View.isFixedSize(widthMeasureSpec: Int, heightMeasureSpec: Int): Boolean {
    val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
    val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)

    return widthMode == View.MeasureSpec.EXACTLY && heightMode == View.MeasureSpec.EXACTLY
}

fun View.isSpecFixed(spec: Int): Boolean {
    val specMode = View.MeasureSpec.getMode(spec)
    return specMode == View.MeasureSpec.EXACTLY
}

fun View.paddingVerticalSum() = paddingTop + paddingBottom

fun View.paddingHorizontalSum() = paddingStart + paddingEnd

inline fun View.addSimpleOnLayoutChangeListener(crossinline listener: () -> Unit) {
    addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
        listener()
    }
}

var View.constraintCircleRadius: Int?
    set(value) = updateLayoutParams<ConstraintLayout.LayoutParams> {
        circleRadius = value ?: return
    }
    get() = (layoutParams as? ConstraintLayout.LayoutParams)?.circleRadius

fun ImageView.setDrawableResIfMissing(drawableRes: Int) {
    if (drawable != null) return

    setImageResource(drawableRes)
}