package com.stochanskyi.nanittask.domain.feature.profile

import com.stochanskyi.nanittask.domain.feature.profile.model.Profile

interface ProfileRepository {
    fun setProfile(profile: Profile)
    fun setProfileImageUri(image: String)

    fun getProfile(): Profile?
}