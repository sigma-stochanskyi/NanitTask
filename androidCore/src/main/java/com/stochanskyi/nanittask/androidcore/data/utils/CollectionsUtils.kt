package com.stochanskyi.nanittask.androidcore.data.utils

fun <T> MutableCollection<T>.addIfNotNull(item: T?) {
    if (item == null) return
    add(item)
}