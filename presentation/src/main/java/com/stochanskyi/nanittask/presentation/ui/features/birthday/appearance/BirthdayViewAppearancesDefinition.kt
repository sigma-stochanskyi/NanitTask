package com.stochanskyi.nanittask.presentation.ui.features.birthday.appearance

interface BirthdayViewAppearancesDefinition {
    fun getRandomViewAppearance(): BirthdayViewAppearance
}

class BirthdayViewAppearancesDefinitionImpl : BirthdayViewAppearancesDefinition {

    private val definitions = mutableListOf(
        BirthdayViewAppearanceBlue(),
        BirthdayViewAppearanceYellow(),
        BirthdayViewAppearanceGreen(),
    )

    override fun getRandomViewAppearance(): BirthdayViewAppearance {
        return definitions.random()
    }
}