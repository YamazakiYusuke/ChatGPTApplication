package com.example.chatgtpapplication.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.chatgtpapplication.repositories.SharedPreferencesRepository

class RegistrationProfileViewModel(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : ViewModel() {

    fun setUserSex(userSex: String, context: Context) {
        sharedPreferencesRepository.setUserSex(userSex, context)
    }

    fun setUserAge(userAge: Int, context: Context) {
        sharedPreferencesRepository.setUserAge(userAge, context)
    }
}