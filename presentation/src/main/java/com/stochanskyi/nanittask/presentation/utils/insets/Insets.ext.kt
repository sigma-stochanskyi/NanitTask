package com.stochanskyi.nanittask.presentation.utils.insets

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.Insets
import androidx.core.view.*

fun ViewGroup.onApplyDispatchInsetsToAllChildren() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { _, windowInsets ->
        dispatchInsetsToChildren(windowInsets)
        WindowInsetsCompat.CONSUMED
    }
}

fun ViewGroup.dispatchInsetsToChildren(windowInsets: WindowInsetsCompat) {
    children.forEach { child ->
        ViewCompat.dispatchApplyWindowInsets(child, windowInsets.copy())
    }
}

fun WindowInsetsCompat.getSystemInsets(): Insets {
    return getInsets(WindowInsetsCompat.Type.systemBars())
}

private fun WindowInsetsCompat.copy() = WindowInsetsCompat(this)

fun View.onApplyDispatchInsetsWithMargins() {
    val margins = Rect(
        marginStart,
        marginTop,
        marginEnd,
        marginBottom
    )

    ViewCompat.setOnApplyWindowInsetsListener(this) { _ , insets ->
        applyInsetsWithMargins(margins, insets)

        WindowInsetsCompat.CONSUMED
    }
}

fun View.onApplyDispatchInsetsWithPaddings() {
    val paddings = Rect(
        paddingStart,
        paddingTop,
        paddingEnd,
        paddingBottom
    )

    ViewCompat.setOnApplyWindowInsetsListener(this) { _ , insets ->
        applyInsetsWithPaddings(paddings, insets)

        WindowInsetsCompat.CONSUMED
    }
}

fun View.applyInsetsWithPaddings(
    initialPaddings: Rect,
    insets: WindowInsetsCompat
) {
    val systemInsets = insets.getSystemInsets()
    if (systemInsets.isEmpty()) return

    setPadding(
        initialPaddings.left + systemInsets.left,
        initialPaddings.top + systemInsets.top,
        initialPaddings.right + systemInsets.right,
        initialPaddings.bottom + systemInsets.bottom
    )
}

fun View.applyInsetsWithMargins(
    initialMargins: Rect,
    insets: WindowInsetsCompat
) {
    val systemInsets = insets.getSystemInsets()
    if (systemInsets.isEmpty()) return

    updateLayoutParams<ViewGroup.MarginLayoutParams> {
        setMargins(
            initialMargins.left + systemInsets.left,
            initialMargins.top + systemInsets.top,
            initialMargins.right + systemInsets.right,
            initialMargins.bottom + systemInsets.bottom
        )
    }
}
private fun Insets.isEmpty(): Boolean {
    return left == 0 && top == 0 && right == 0 && bottom == 0
}