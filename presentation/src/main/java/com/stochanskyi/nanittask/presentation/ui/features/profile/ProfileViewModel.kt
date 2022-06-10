package com.stochanskyi.nanittask.presentation.ui.features.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stochanskyi.nanittask.androidcore.data.utils.utcLocalDate
import com.stochanskyi.nanittask.presentation.utils.SingleLiveAction
import java.time.LocalDate

abstract class ProfileViewModel : ViewModel() {

    abstract val birthdayLiveData: LiveData<LocalDate?>
    abstract val imageUriLiveData: LiveData<String>

    abstract val openBirthdayEnabledLiveData: LiveData<Boolean>
    abstract val openBirthdayLiveData: LiveData<Unit>

    abstract fun setName(name: String)
    abstract fun setDate(dateMillis: Long)
    abstract fun setImageUri(uri: String)
    abstract fun openBirthday()
}

class ProfileViewModelImpl : ProfileViewModel() {

    private var name: String = ""
    private var date: LocalDate? = null
    private var imageUri: String = ""

    override val birthdayLiveData = MutableLiveData<LocalDate?>(date)
    override val imageUriLiveData = MutableLiveData(imageUri)

    override val openBirthdayEnabledLiveData = MutableLiveData(false)
    override val openBirthdayLiveData = SingleLiveAction()

    override fun setName(name: String) {
        this.name = name
        updateBirthdayEnabled()
    }

    override fun setDate(dateMillis: Long) {
        this.date = utcLocalDate(dateMillis)
        birthdayLiveData.value = date
        updateBirthdayEnabled()
    }

    override fun setImageUri(uri: String) {
        this.imageUri = uri
        imageUriLiveData.value = uri
        updateBirthdayEnabled()
    }

    override fun openBirthday() {
        openBirthdayLiveData.call()
    }

    private fun updateBirthdayEnabled() {
        val isBirthdayEnabled = name.isNotBlank() &&
            date != null

        openBirthdayEnabledLiveData.value = isBirthdayEnabled
    }
}