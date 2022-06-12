package com.stochanskyi.nanittask.domain.feature.profile.model

data class Age(
    val years: Int,
    val months: Int,
) {
    val isYoungerThenYear: Boolean
        get() = years <= 0
}