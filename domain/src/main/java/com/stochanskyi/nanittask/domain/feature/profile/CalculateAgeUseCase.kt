package com.stochanskyi.nanittask.domain.feature.profile

import com.stochanskyi.nanittask.domain.feature.profile.model.Age
import java.time.LocalDate
import java.time.temporal.ChronoUnit

interface CalculateAgeUseCase {
    operator fun invoke(birthday: LocalDate): Age
}

class CalculateAgeUseCaseImpl : CalculateAgeUseCase {

    override fun invoke(birthday: LocalDate): Age {
        val currentDate = LocalDate.now()

        val months = ChronoUnit.MONTHS.between(currentDate, birthday)
        val years = ChronoUnit.YEARS.between(currentDate, birthday)

        return Age(
            months = months.toInt(),
            years = years.toInt(),
        )
    }
}