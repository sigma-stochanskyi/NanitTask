package com.stochanskyi.nanittask.data.repositories

import com.stochanskyi.nanittask.data.storage.ProfileDataStorage
import com.stochanskyi.nanittask.domain.feature.profile.ProfileRepository
import com.stochanskyi.nanittask.domain.feature.profile.model.Profile
import java.time.LocalDate

class ProfileRepositoryImpl(
    private var storage: ProfileDataStorage
) : ProfileRepository {

    override fun setProfile(profile: Profile) {
        storage.setProfileName(profile.name)
        storage.setBirthday(profile.birthday.toString())

        profile.imageUri?.let {
            storage.setProfileImageUri(it)
        }
    }

    override fun setProfileImageUri(image: String) {
        storage.setProfileImageUri(image)
    }

    override fun getProfile(): Profile? {
        return Profile(
            name = storage.getProfileName() ?: return null,
            birthday = LocalDate.parse(storage.getBirthday() ?: return null),
            imageUri = storage.getProfileImageUri()
        )
    }
}