package com.stochanskyi.nanittask.presentation.utils

import android.view.View

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