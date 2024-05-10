package com.ZA.tasbi

import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings.Global.putString
import androidx.core.content.edit

class AppPreferences(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    var accessCounter: String?
        get() = preferences.getString("accessCounter", "0")
        set(value) = setValue("accessCounter", value)

    var accessRap: String?
        get() = preferences.getString("accessRap", "0")
        set(value) = setValue("accessRap", value)


    private inline fun <reified T> setValue(key: String, value: T) {
        preferences.edit {
            when (value) {
                is String -> putString(key, value).apply()
                is Int -> putInt(key, value).apply()
                is Boolean -> putBoolean(key, value).apply()
                is Long -> putLong(key, value).apply()
                is Float -> putFloat(key, value).apply()
            }
        }
    }
}