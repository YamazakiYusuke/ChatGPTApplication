package com.example.chatgtpapplication.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.chatgtpapplication.R
import com.example.chatgtpapplication.databinding.FragmentSetSecretKeyBinding
import com.example.chatgtpapplication.repositories.ChatGDPRepository
import com.example.chatgtpapplication.repositories.SharedPreferencesRepository
import com.example.chatgtpapplication.viewmodels.SetSecretKeyViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SetSecretKeyFragment : Fragment() {

    private lateinit var binding: FragmentSetSecretKeyBinding
    private val viewModel =
        SetSecretKeyViewModel(ChatGDPRepository(), SharedPreferencesRepository())
    var handler: Handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSetSecretKeyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.checkAndRegisterApiKey.setOnClickListener {
            checkAndSetApiKey()
        }
    }

    /**
     * API key有効確認、登録処理
     */
    private fun checkAndSetApiKey() {
        val apiKey = binding.apiKeyTextField.editText?.text.toString()
        lifecycleScope.launch(Dispatchers.IO) {
            val result = viewModel.checkAPIKey(apiKey)
            if (result) {
                viewModel.setApiKey(apiKey, requireContext())
                handler.post {
                    Toast.makeText(context, "API keyが正しく登録されました。", Toast.LENGTH_LONG).show()
                }
                changeFragment()
            } else {
                handler.post {
                    Toast.makeText(context, "API keyが正しくありません。", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    /**
     * プロフィール画面へ切り替え処理
     */
    private fun changeFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, RegistrationProfileFragment()).commit()
    }
}