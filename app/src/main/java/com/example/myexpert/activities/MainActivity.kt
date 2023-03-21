package com.example.myexpert.activities

import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myexpert.R
import com.example.myexpert.databinding.ActivityMainBinding
import com.example.myexpert.fragments.WelcomeFragment
import com.example.myexpert.fragments.RegistrationProfileFragment
import com.example.myexpert.fragments.SelectExpertFragment
import com.example.myexpert.fragments.SetApiKeyFragment
import com.example.myexpert.repositories.ChatGPTRepository
import com.example.myexpert.repositories.SharedPreferencesRepository
import com.example.myexpert.viewmodels.MainViewModel
import com.example.myexpert.views.ToolBarCustomView
import com.example.myexpert.views.ToolBarCustomViewDelegate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ToolBarCustomViewDelegate {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    lateinit var toolBarCustomView: ToolBarCustomView
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // デフォルトのアクションバーを非表示にする
        supportActionBar?.hide()
        // カスタムツールバーを設置
        initCustomToolBar()
        router()
    }

    private fun initCustomToolBar() {
        toolBarCustomView = ToolBarCustomView(this)
        toolBarCustomView.delegate = this

        val title = getString(R.string.app_name)
        toolBarCustomView.configure(title, true)

        // カスタムツールバーを挿入するコンテナ(入れ物)を指定
        val layout: LinearLayout = binding.containerForToolbar
        // ツールバーの表示をコンテナに合わせる
        toolBarCustomView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        // カスタムツールバーを表示する
        layout.addView(toolBarCustomView)
    }

    /**
     * ツールバー戻るボタン押下時の処理
     */
    override fun onClickedLeftButton() {
        onBackPressed()
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
            addFragment(WelcomeFragment())
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
                    addFragment(SetApiKeyFragment())
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