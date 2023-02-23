package com.example.chatgtpapplication.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.chatgtpapplication.repositories.SharedPreferencesRepository

class RegistrationProfileViewModel(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : ViewModel() {

    /**
     * 性別をSharedPreferencesに保存
     * @param userSex
     * @param context
     */
    fun setUserSex(userSex: String, context: Context) {
        sharedPreferencesRepository.setUserSex(userSex, context)
    }

    /**
     * 年齢をSharedPreferencesに保存
     * @param userAge
     * @param context
     */
    fun setUserAge(userAge: Int, context: Context) {
        sharedPreferencesRepository.setUserAge(userAge, context)
    }
}