package com.stochanskyi.nanittask.domain.feature.profile.model

import java.time.LocalDate

data class Profile(
    val name: String,
    val birthday: LocalDate,
    val imageUri: String?
)