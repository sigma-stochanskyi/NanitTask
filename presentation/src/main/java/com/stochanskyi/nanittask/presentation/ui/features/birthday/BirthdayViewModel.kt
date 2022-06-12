package com.stochanskyi.nanittask.presentation.ui.features.birthday

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stochanskyi.nanittask.domain.feature.profile.CalculateAgeUseCase
import com.stochanskyi.nanittask.domain.feature.profile.GetProfileUseCase
import com.stochanskyi.nanittask.domain.feature.profile.SetProfileImageUseCase
import com.stochanskyi.nanittask.domain.feature.profile.model.Age
import com.stochanskyi.nanittask.presentation.R
import com.stochanskyi.nanittask.presentation.ui.features.birthday.appearance.BirthdayViewAppearance
import com.stochanskyi.nanittask.presentation.ui.features.birthday.appearance.BirthdayViewAppearancesDefinition
import com.stochanskyi.nanittask.presentation.ui.features.birthday.model.AgeInfoViewData

abstract class BirthdayViewModel : ViewModel() {

    abstract val viewAppearanceLiveData: LiveData<BirthdayViewAppearance>

    abstract val nameLiveData: LiveData<String>
    abstract val ageInfoLiveData: LiveData<AgeInfoViewData>
    abstract val imageLiveData: LiveData<String?>

    abstract fun setImageUri(imageUri: String)
}

class BirthdayViewModelImpl(
    private val getProfileUseCase: GetProfileUseCase,
    private val calculateAgeUseCase: CalculateAgeUseCase,
    private val setProfileImageUseCase: SetProfileImageUseCase,
    private val viewAppearancesDefinition: BirthdayViewAppearancesDefinition,
) : BirthdayViewModel() {

    override val viewAppearanceLiveData = MutableLiveData<BirthdayViewAppearance>()
    override val nameLiveData = MutableLiveData<String>()

    override val ageInfoLiveData = MutableLiveData<AgeInfoViewData>()
    override val imageLiveData = MutableLiveData<String?>()

    init {
        setupViewAppearance()
        loadBirthdayInfo()
    }

    override fun setImageUri(imageUri: String) {
        setProfileImageUseCase(imageUri)
        imageLiveData.value = imageUri
    }

    private fun setupViewAppearance() {
        viewAppearanceLiveData.value = viewAppearancesDefinition.getRandomViewAppearance()
    }

    private fun loadBirthdayInfo() {
        val profile = getProfileUseCase() ?: return
        val age = calculateAgeUseCase(profile.birthday)

        nameLiveData.value = profile.name
        imageLiveData.value = profile.imageUri

        updateBirthdayData(age)
    }

    private fun updateBirthdayData(age: Age) {
        ageInfoLiveData.value = AgeInfoViewData(
            ageValue = age.getValue(),
            ageUnitPluralRes = age.getUnitStringRes(),
        )
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