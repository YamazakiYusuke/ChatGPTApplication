package jp.yusuke.myexpert.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import jp.yusuke.myexpert.R
import jp.yusuke.myexpert.databinding.FragmentSetApiKeyBinding
import jp.yusuke.myexpert.viewmodels.SetApiKeyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SetApiKeyFragment : Fragment() {

    private var _binding: FragmentSetApiKeyBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SetApiKeyViewModel by viewModels()
    private var canClickButton = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSetApiKeyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.checkAndRegisterApiKey.setOnClickListener {
            if (!canClickButton) {
                return@setOnClickListener
            }
            binding.indicator.visibility = View.VISIBLE

            try {
                checkAndSetApiKey()
            } catch (e: Exception) {
                Log.e(tag, e.toString())
            } finally {
                binding.indicator.visibility = View.GONE
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