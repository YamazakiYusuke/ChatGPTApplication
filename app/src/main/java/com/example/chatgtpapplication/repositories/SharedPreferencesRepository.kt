package com.example.chatgtpapplication.repositories

import android.content.Context
import android.content.SharedPreferences
import com.example.chatgtpapplication.utils.Const

class SharedPreferencesRepository {
    companion object {
        const val SharedPreferencesData = "SharedPreferencesData"
        const val apiKeyKey = "apiKeyKey"
    }

    fun setApiKey(apiKey: String, context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(SharedPreferencesData, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(apiKeyKey, apiKey)
        editor.apply()
    }

    fun getApiKey(context: Context): String {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(SharedPreferencesData, Context.MODE_PRIVATE)
        return sharedPreferences.getString(apiKeyKey, "") ?: ""
    }
}