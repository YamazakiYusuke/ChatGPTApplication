package jp.yusuke.myexpert.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import jp.yusuke.myexpert.repositories.ChatGPTRepository
import jp.yusuke.myexpert.repositories.SharedPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SetApiKeyViewModel @Inject constructor(
    private val chatGDPRepository: ChatGPTRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : ViewModel() {

    /**
     * ApiKeyが有効化を確認
     * @param apiKey
     * @return true(有効) / false(無効)
     */
    fun checkAPIKey(apiKey: String): Boolean {
        return chatGDPRepository.checkAPIKey(apiKey)
    }

    /**
     * Api keyをSharedPreferencesに保存
     * @param apiKey
     * @param context
     */
    fun setApiKey(apiKey: String, context: Context) {
        sharedPreferencesRepository.setApiKey(apiKey, context)
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