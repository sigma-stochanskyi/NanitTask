package com.stochanskyi.nanittask.data.repositories

import com.stochanskyi.nanittask.data.storage.ProfileDataStorage
import com.stochanskyi.nanittask.domain.feature.profile.ProfileRepository
import com.stochanskyi.nanittask.domain.feature.profile.model.Profile
import java.time.LocalDate

class ProfileRepositoryImpl(
    private var storage: ProfileDataStorage
) : ProfileRepository {

    override fun setProfile(profile: Profile) {
        profile.name?.let {
            storage.setProfileName(it)
        }
        profile.birthday?.toString()?.let {
            storage.setBirthday(it)
        }
        profile.imageUri?.let {
            storage.setProfileImageUri(it)
        }
    }

    override fun setProfileImageUri(image: String) {
        storage.setProfileImageUri(image)
    }

    override fun getProfile(): Profile {
        return Profile(
            name = storage.getProfileName(),
            birthday = storage.getBirthday()?.let { LocalDate.parse(storage.getBirthday()) },
            imageUri = storage.getProfileImageUri()
        )
    }
}