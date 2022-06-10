package com.stochanskyi.nanittask.presentation.ui.features.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stochanskyi.nanittask.androidcore.data.utils.utcLocalDate
import com.stochanskyi.nanittask.domain.feature.profile.GetProfileUseCase
import com.stochanskyi.nanittask.domain.feature.profile.SetProfileUseCase
import com.stochanskyi.nanittask.domain.feature.profile.model.Profile
import com.stochanskyi.nanittask.presentation.utils.SingleLiveAction
import java.time.LocalDate

abstract class ProfileViewModel : ViewModel() {

    abstract val nameLiveData: LiveData<String?>
    abstract val birthdayLiveData: LiveData<LocalDate?>
    abstract val imageUriLiveData: LiveData<String?>

    abstract val openBirthdayEnabledLiveData: LiveData<Boolean>
    abstract val openBirthdayLiveData: LiveData<Unit>

    abstract fun loadProfile()

    abstract fun setName(name: String?)
    abstract fun setDate(dateMillis: Long)
    abstract fun setImageUri(uri: String?)
    abstract fun openBirthday()
}

class ProfileViewModelImpl(
    private val getProfileUseCase: GetProfileUseCase,
    private val setProfileUseCase: SetProfileUseCase
) : ProfileViewModel() {

    private var name: String? = null
    private var date: LocalDate? = null
    private var imageUri: String? = null

    override val nameLiveData = MutableLiveData<String?>()
    override val birthdayLiveData = MutableLiveData<LocalDate?>(date)
    override val imageUriLiveData = MutableLiveData(imageUri)

    override val openBirthdayEnabledLiveData = MutableLiveData(false)
    override val openBirthdayLiveData = SingleLiveAction()

    override fun setName(name: String?) {
        this.name = name
        nameLiveData.value = name
        updateBirthdayEnabled()
    }

    override fun setDate(dateMillis: Long) {
        setDate(utcLocalDate(dateMillis))
    }

    private fun setDate(date: LocalDate?) {
        this.date = date
        birthdayLiveData.value = date
        updateBirthdayEnabled()
    }

    override fun setImageUri(uri: String?) {
        this.imageUri = uri
        imageUriLiveData.value = uri
        updateBirthdayEnabled()
    }

    override fun openBirthday() {
        val profile = createProfile()
        setProfileUseCase(profile)

        openBirthdayLiveData.call()
    }

    override fun loadProfile() {
        val profile = getProfileUseCase()

        setName(profile.name)
        setDate(profile.birthday)
        setImageUri(profile.imageUri)
    }

    private fun createProfile(): Profile {
        return Profile(
            name = name,
            birthday = date,
            imageUri = imageUri
        )
    }

    private fun updateBirthdayEnabled() {
        val isBirthdayEnabled = name?.isNotBlank() == true &&
            date != null

        openBirthdayEnabledLiveData.value = isBirthdayEnabled
    }
}