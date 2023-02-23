package com.example.chatgtpapplication.repositories

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class SharedPreferencesRepository {
    companion object {
        const val SharedPreferencesData = "SharedPreferencesData"
        const val apiKeyKey = "apiKeyKey"
        const val userSexKey = "userSexKey"
        const val userAgeKey = "userAgeKey"
    }

    private val tag = "SharedPreferencesRepository"

    /**
     * Api keyをSharedPreferencesに保存
     */
    fun setApiKey(apiKey: String, context: Context) {
        Log.i(tag, "call setApiKey")
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(SharedPreferencesData, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(apiKeyKey, apiKey)
        editor.apply()
    }

    /**
     * Api keyをSharedPreferencesから取得
     */
    fun getApiKey(context: Context): String {
        Log.i(tag, "call getApiKey")
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(SharedPreferencesData, Context.MODE_PRIVATE)
        return sharedPreferences.getString(apiKeyKey, "") ?: ""
    }

    /**
     * 性別をSharedPreferencesに保存
     */
    fun setUserSex(userSex: String, context: Context) {
        Log.i(tag, "call setUserSex")
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(SharedPreferencesData, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(userSexKey, userSex)
        editor.apply()
    }

    /**
     * 性別をSharedPreferencesから取得
     */
    fun getUserSex(context: Context): String {
        Log.i(tag, "call getUserSex")
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(SharedPreferencesData, Context.MODE_PRIVATE)
        return sharedPreferences.getString(userSexKey, "") ?: ""
    }

    /**
     * 年齢をSharedPreferencesに保存
     */
    fun setUserAge(userAge: Int, context: Context) {
        Log.i(tag, "call setUserAge")
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(SharedPreferencesData, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(userAgeKey, userAge)
        editor.apply()
    }

    /**
     * 年齢をSharedPreferencesから取得
     */
    fun getUserAge(context: Context): Int {
        Log.i(tag, "call getUserAge")
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(SharedPreferencesData, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(userAgeKey, -1)
    }
}