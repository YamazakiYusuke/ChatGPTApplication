package com.example.myexpert.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myexpert.R
import com.example.myexpert.fragment.RegistrationProfileFragment
import com.example.myexpert.fragment.SelectExpertFragment
import com.example.myexpert.fragment.SetSecretKeyFragment
import com.example.myexpert.repositories.ChatGDPRepository
import com.example.myexpert.repositories.SharedPreferencesRepository
import com.example.myexpert.viewmodels.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val viewModel = MainViewModel(ChatGDPRepository(), SharedPreferencesRepository())

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        router()
    }

    /**
     * 登録情報より表示画面を決定
     */
    private fun router() {
        val apiKey = viewModel.getApiKey(this)
        val userSex = viewModel.getUserSex(this)
        val userAge = viewModel.getUserAge(this)
        if (apiKey.isBlank()) {
            addFragment(SetSecretKeyFragment())
            return
        }
        if (userSex.isBlank() || userAge == -1) {
            addFragment(RegistrationProfileFragment())
            return
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val isApiKeyEnable = viewModel.checkAPIKey(apiKey)
            withContext(Dispatchers.Main) {
                if (isApiKeyEnable) {
                    addFragment(SelectExpertFragment())
                } else {
                    Toast.makeText(this@MainActivity, "登録済みのAPI keyは失効しております。", Toast.LENGTH_LONG)
                        .show()
                    addFragment(SetSecretKeyFragment())
                }
            }
        }
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.frameLayout, fragment)
            .commit()
    }
}