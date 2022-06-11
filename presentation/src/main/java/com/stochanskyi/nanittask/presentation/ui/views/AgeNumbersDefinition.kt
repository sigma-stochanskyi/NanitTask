package com.stochanskyi.nanittask.presentation.ui.views

import androidx.annotation.DrawableRes
import androidx.annotation.IntRange
import com.stochanskyi.nanittask.presentation.R

class AgeNumbersDefinition {

    private val definitions = mapOf(
        0 to R.drawable.ic_age_0,
        1 to R.drawable.ic_age_1,
        2 to R.drawable.ic_age_2,
        3 to R.drawable.ic_age_3,
        4 to R.drawable.ic_age_4,
        5 to R.drawable.ic_age_5,
        6 to R.drawable.ic_age_6,
        7 to R.drawable.ic_age_7,
        8 to R.drawable.ic_age_8,
        9 to R.drawable.ic_age_9,
    )

    @DrawableRes
    fun getAgeNumberDrawableRes(
        @IntRange(from = 0, to = 9) age: Int
    ): Int {
        return definitions.getOrElse(age) {
            throw IllegalStateException("No definition found for age $age")
        }
    }

    @DrawableRes
    fun getAgeNumberDrawableResOrNull(
        @IntRange(from = 0, to = 9) age: Int
    ): Int? {
        return definitions[age]
    }
}