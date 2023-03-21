package com.example.myexpert.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myexpert.R
import com.example.myexpert.databinding.FragmentRegistrationProfileBinding
import com.example.myexpert.repositories.SharedPreferencesRepository
import com.example.myexpert.viewmodels.RegistrationProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationProfileFragment : Fragment() {

    private var _binding: FragmentRegistrationProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegistrationProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationProfileBinding.inflate(inflater, container, false)

        binding.buttonSubmit.setOnClickListener {
            if (validation()) {
                showDialog()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * 入力値の性別を取得
     * @return String
     */
    private fun getGender(): String {
        val selectedRadioButtonId = binding.radioGroupGender.checkedRadioButtonId
        val radioButton = binding.root.findViewById<RadioButton>(selectedRadioButtonId)
        return radioButton.text.toString()
    }

    /**
     * 入力値バリデーション
     * @return true(入力値正常) / false(入力値異常)
     */
    private fun validation(): Boolean {
        var result = true
        val selectedRadioButtonId = binding.radioGroupGender.checkedRadioButtonId
        if (selectedRadioButtonId == -1) {
            Toast.makeText(activity, "性別を選択してください。", Toast.LENGTH_LONG).show()
            result = false
        }
        val age = getUserAge()
        if (age == null) {
            Toast.makeText(activity, "年齢を入力してくだいさい。", Toast.LENGTH_LONG).show()
            result = false
        }
        return result
    }

    /**
     * 入力値の年齢を取得
     * @return Int
     */
    private fun getUserAge(): Int? {
        val text = binding.textFieldAge.editText?.text?.toString()
        if (text.isNullOrBlank()) {
            return null
        }
        return text.toInt()

    }

    private fun showDialog() {
        AlertDialog.Builder(context)
            .setTitle("登録内容確認")
            .setMessage(
                """
                性別 : ${getGender()}
                年齢 : ${getUserAge()}歳
            """.trimIndent()
            )
            .setPositiveButton("登録") { _, _ ->
                saveProfileData()
                changeFragment()
            }
            .setNegativeButton("キャンセル") { _, _ ->
                // 何もしない
            }
            .show()
    }

    private fun saveProfileData() {
        viewModel.setUserSex(getGender(), requireContext())
        viewModel.setUserAge(getUserAge()!!, requireContext())
    }

    private fun changeFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, SelectExpertFragment()).commit()
    }
}