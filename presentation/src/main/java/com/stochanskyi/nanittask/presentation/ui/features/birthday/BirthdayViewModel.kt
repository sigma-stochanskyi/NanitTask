package com.stochanskyi.nanittask.presentation.ui.features.birthday

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stochanskyi.nanittask.presentation.ui.features.birthday.appearance.BirthdayViewAppearance
import com.stochanskyi.nanittask.presentation.ui.features.birthday.appearance.BirthdayViewAppearancesDefinition

abstract class BirthdayViewModel : ViewModel() {

    abstract val viewAppearanceLiveData: LiveData<BirthdayViewAppearance>

}

class BirthdayViewModelImpl(
    viewAppearancesDefinition: BirthdayViewAppearancesDefinition
) : BirthdayViewModel() {

    override val viewAppearanceLiveData = MutableLiveData<BirthdayViewAppearance>()

    init {
        viewAppearanceLiveData.value = viewAppearancesDefinition.getRandomViewAppearance()
    }
}