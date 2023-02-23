package com.example.chatgtpapplication.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.chatgtpapplication.repositories.SharedPreferencesRepository

class MainViewModel(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : ViewModel() {
    /**
     * Api keyをSharedPreferencesから取得
     * @return ApiKey 存在しない場合は空の文字列
     */
    fun getApiKey(context: Context): String {
        return sharedPreferencesRepository.getApiKey(context)
    }

    /**
     * 性別をSharedPreferencesから取得
     * @return UserSex 存在しない場合は空の文字列
     */
    fun getUserSex(context: Context): String {
        return sharedPreferencesRepository.getUserSex(context)
    }

    /**
     * 年齢をSharedPreferencesから取得
     * @return UserAge 存在しない場合は-1
     */
    fun getUserAge(context: Context): Int {
        return sharedPreferencesRepository.getUserAge(context)

    }
}