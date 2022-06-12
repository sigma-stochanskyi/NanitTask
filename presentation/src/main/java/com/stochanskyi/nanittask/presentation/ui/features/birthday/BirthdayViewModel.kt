package com.stochanskyi.nanittask.presentation.ui.features.birthday

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stochanskyi.nanittask.domain.feature.profile.CalculateAgeUseCase
import com.stochanskyi.nanittask.domain.feature.profile.GetProfileUseCase
import com.stochanskyi.nanittask.domain.feature.profile.model.Age
import com.stochanskyi.nanittask.presentation.R
import com.stochanskyi.nanittask.presentation.ui.features.birthday.appearance.BirthdayViewAppearance
import com.stochanskyi.nanittask.presentation.ui.features.birthday.appearance.BirthdayViewAppearancesDefinition
import com.stochanskyi.nanittask.presentation.ui.features.birthday.model.BirthdayInfoViewData

abstract class BirthdayViewModel : ViewModel() {

    abstract val viewAppearanceLiveData: LiveData<BirthdayViewAppearance>

    abstract val birthdayInfoLiveData: LiveData<BirthdayInfoViewData>

}

class BirthdayViewModelImpl(
    private val getProfileUseCase: GetProfileUseCase,
    private val calculateAgeUseCase: CalculateAgeUseCase,
    private val viewAppearancesDefinition: BirthdayViewAppearancesDefinition,
) : BirthdayViewModel() {

    override val viewAppearanceLiveData = MutableLiveData<BirthdayViewAppearance>()

    override val birthdayInfoLiveData = MutableLiveData<BirthdayInfoViewData>()

    init {
        setupViewAppearance()
        loadBirthdayInfo()
    }

    private fun setupViewAppearance() {
        viewAppearanceLiveData.value = viewAppearancesDefinition.getRandomViewAppearance()
    }

    private fun loadBirthdayInfo() {
        val profile = getProfileUseCase() ?: return
        val age = calculateAgeUseCase(profile.birthday)

        val viewData = BirthdayInfoViewData(
            name = profile.name,
            ageValue = age.getValue(),
            ageUnitPluralRes = age.getUnitStringRes(),
            imageUri = profile.imageUri
        )

        birthdayInfoLiveData.value = viewData
    }

    private fun Age.getValue(): Int {
        return if (isYoungerThenYear) months else years
    }

    private fun Age.getUnitStringRes(): Int {
        return if (isYoungerThenYear) {
            R.plurals.age_months
        } else {
            R.plurals.age_years
        }
    }
}