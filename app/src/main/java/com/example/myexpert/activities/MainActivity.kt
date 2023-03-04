package com.example.myexpert.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myexpert.R
import com.example.myexpert.databinding.ActivityMainBinding
import com.example.myexpert.fragments.RegistrationProfileFragment
import com.example.myexpert.fragments.SelectExpertFragment
import com.example.myexpert.fragments.SetSecretKeyFragment
import com.example.myexpert.repositories.ChatGDPRepository
import com.example.myexpert.repositories.SharedPreferencesRepository
import com.example.myexpert.viewmodels.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel = MainViewModel(ChatGDPRepository(), SharedPreferencesRepository())

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        router()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commit()
    }
}