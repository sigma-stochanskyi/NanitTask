package com.stochanskyi.nanittask.presentation.ui.features.birthday.model

import androidx.annotation.PluralsRes

class BirthdayInfoViewData(
    val name: String,
    val ageValue: Int,
    @PluralsRes val ageUnitPluralRes: Int,
    val imageUri: String?,
)