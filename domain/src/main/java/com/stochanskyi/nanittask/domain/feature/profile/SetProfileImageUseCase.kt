package com.stochanskyi.nanittask.domain.feature.profile

interface SetProfileImageUseCase {
    operator fun invoke(imageUri: String)
}

class SetProfileImageUseCaseImpl(
    private val repository: ProfileRepository
) : SetProfileImageUseCase {

    override fun invoke(imageUri: String) {
        repository.setProfileImageUri(imageUri)
    }
}