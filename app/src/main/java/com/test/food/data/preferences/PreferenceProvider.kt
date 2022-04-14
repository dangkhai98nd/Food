package com.test.food.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

private const val KEY_SAVED_AT = "key_saved_at"
private const val KEY_IS_LOGIN = "key_is_login"

class PreferenceProvider(
    context: Context
) {

    private val appContext = context.applicationContext

    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)


    fun saveLastSavedAt(savedAt: String) {
        preference.edit().putString(
            KEY_SAVED_AT,
            savedAt
        ).apply()
    }

    fun getLastSavedAt(): String? {
        return preference.getString(KEY_SAVED_AT, null)
    }

    fun saveLogin(login: Boolean) {
        preference.edit().putBoolean(
            KEY_IS_LOGIN,
            login
        ).apply()
    }

    fun getLoginUser(): Boolean? {
        return preference.getBoolean(KEY_IS_LOGIN, false)
    }

}