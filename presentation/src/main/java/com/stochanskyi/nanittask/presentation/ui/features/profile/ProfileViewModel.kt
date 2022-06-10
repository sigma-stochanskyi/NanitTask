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

    abstract val isOpenBirthdayEnabledLiveData: LiveData<Boolean>
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

    override val isOpenBirthdayEnabledLiveData = MutableLiveData(false)
    override val openBirthdayLiveData = SingleLiveAction()

    override fun setName(name: String?) = updateProfileParam {
        this.name = name
        nameLiveData.value = name
    }

    override fun setDate(dateMillis: Long) {
        setDate(utcLocalDate(dateMillis))
    }

    private fun setDate(date: LocalDate?) = updateProfileParam {
        this.date = date
        birthdayLiveData.value = date
    }

    override fun setImageUri(uri: String?) = updateProfileParam {
        this.imageUri = uri
        imageUriLiveData.value = uri
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

    private inline fun updateProfileParam(updateBlock: () -> Unit) {
        updateBlock()
        updateBirthdayEnabled()
    }

    private fun updateBirthdayEnabled() {
        val isBirthdayEnabled = name?.isNotBlank() == true &&
            date != null

        isOpenBirthdayEnabledLiveData.value = isBirthdayEnabled
    }
}