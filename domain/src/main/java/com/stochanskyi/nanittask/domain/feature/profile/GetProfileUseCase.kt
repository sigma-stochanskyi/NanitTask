package com.stochanskyi.nanittask.domain.feature.profile

import com.stochanskyi.nanittask.domain.feature.profile.model.Profile

interface GetProfileUseCase {
    operator fun invoke(): Profile?
}

class GetProfileUseCaseImpl(
    private val profileRepository: ProfileRepository
) : GetProfileUseCase {

    override fun invoke(): Profile? {
        return profileRepository.getProfile()
    }
}