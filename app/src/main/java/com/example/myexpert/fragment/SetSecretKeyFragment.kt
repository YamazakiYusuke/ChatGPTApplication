package com.example.myexpert.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myexpert.R
import com.example.myexpert.databinding.FragmentSetSecretKeyBinding
import com.example.myexpert.repositories.ChatGDPRepository
import com.example.myexpert.repositories.SharedPreferencesRepository
import com.example.myexpert.viewmodels.SetSecretKeyViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SetSecretKeyFragment : Fragment() {

    private var _binding: FragmentSetSecretKeyBinding? = null
    private val binding get() = _binding!!
    private val viewModel =
        SetSecretKeyViewModel(ChatGDPRepository(), SharedPreferencesRepository())
    private var canClickButton = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSetSecretKeyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.checkAndRegisterApiKey.setOnClickListener {
            if (!canClickButton) {
                return@setOnClickListener
            }

            try {
                checkAndSetApiKey()
            } catch (e: Exception) {
                Log.e(tag, e.toString())
            } finally {
                canClickButton = true
            }
        }

        binding.apiKeyHint.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, ApiKeyHintFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * API key有効確認、登録処理
     * @return true(処理成功) / false(処理失敗)
     */
    private fun checkAndSetApiKey() {
        val apiKey = binding.apiKeyTextField.editText?.text.toString()
        if (apiKey.isBlank()) {
            Toast.makeText(context, "API keyを入力してください。", Toast.LENGTH_LONG).show()
        }
        lifecycleScope.launch(Dispatchers.IO) {
            val result = viewModel.checkAPIKey(apiKey)
            withContext(Dispatchers.Main) {
                if (result) {
                    viewModel.setApiKey(apiKey, requireContext())
                    Toast.makeText(context, "API keyが正しく登録されました。", Toast.LENGTH_LONG).show()
                    changeFragment()
                } else {
                    Toast.makeText(context, "API keyが正しくありません。", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    /**
     * 画面の切り替え処理
     */
    private fun changeFragment() {
        val userSex = viewModel.getUserSex(requireContext())
        val userAge = viewModel.getUserAge(requireContext())
        if (userSex.isBlank() || userAge == -1) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, RegistrationProfileFragment()).commit()
        } else {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, SelectExpertFragment()).commit()
        }

    }
}