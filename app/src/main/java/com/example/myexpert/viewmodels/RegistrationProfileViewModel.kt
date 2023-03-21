package com.example.myexpert.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.myexpert.repositories.SharedPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationProfileViewModel @Inject constructor(
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