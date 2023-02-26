package com.example.myexpert.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.myexpert.repositories.ChatGDPRepository
import com.example.myexpert.repositories.SharedPreferencesRepository

class MainViewModel(
    private val chatGDPRepository: ChatGDPRepository,
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

    /**
     * ApiKeyが有効化を確認
     * @param apiKey
     * @return true(有効) / false(無効)
     */
    fun checkAPIKey(apiKey: String): Boolean {
        return chatGDPRepository.checkAPIKey(apiKey)
    }
}