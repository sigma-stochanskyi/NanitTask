package com.stochanskyi.nanittask.data.storage

import android.content.Context
import androidx.core.content.edit

interface ProfileDataStorage {
    fun setProfileName(name: String)
    fun setBirthday(date: String)
    fun setProfileImageUri(image: String)

    fun getProfileName(): String?
    fun getBirthday(): String?
    fun getProfileImageUri(): String?
}

class ProfileDataStorageImpl(
    context: Context
) : ProfileDataStorage {

    companion object {
        private const val PREFERENCES_NAME = "profile_storage"

        private const val PROFILE_NAME_KEY = "profile_name"
        private const val BIRTHDAY_KEY = "birthday"
        private const val PROFILE_IMAGE_KEY = "profile_image"
    }

    private val prefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun setProfileName(name: String) {
        prefs?.edit {
            putString(PROFILE_NAME_KEY, name)
        }
    }

    override fun setBirthday(date: String) {
        prefs?.edit {
            putString(BIRTHDAY_KEY, date)
        }
    }

    override fun setProfileImageUri(image: String) {
        prefs?.edit {
            putString(PROFILE_IMAGE_KEY, image)
        }
    }

    override fun getProfileName(): String? {
        return prefs.getString(PROFILE_NAME_KEY, null)
    }

    override fun getBirthday(): String? {
        return prefs.getString(BIRTHDAY_KEY, null)
    }

    override fun getProfileImageUri(): String? {
        return prefs.getString(PROFILE_IMAGE_KEY, null)
    }
}