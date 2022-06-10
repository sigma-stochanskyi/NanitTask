package com.stochanskyi.nanittask.domain.feature.profile

import com.stochanskyi.nanittask.domain.feature.profile.model.Profile

interface SetProfileUseCase {
    operator fun invoke(profile: Profile)
}

class SetProfileUseCaseImpl(
    private val repository: ProfileRepository
) : SetProfileUseCase {

    override fun invoke(profile: Profile) {
        repository.setProfile(profile)
    }
}