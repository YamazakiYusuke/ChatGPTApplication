package com.example.chatgtpapplication.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.example.chatgtpapplication.R
import com.example.chatgtpapplication.databinding.FragmentRegistrationProfileBinding
import com.example.chatgtpapplication.repositories.SharedPreferencesRepository
import com.example.chatgtpapplication.viewmodels.RegistrationProfileViewModel


class RegistrationProfileFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationProfileBinding
    private val viewModel = RegistrationProfileViewModel(SharedPreferencesRepository())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationProfileBinding.inflate(inflater, container, false)

        binding.seekBarAge.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.textViewAge.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.buttonSubmit.setOnClickListener {
            showDialog()
        }

        return binding.root
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
     * 入力値の年齢を取得
     * @return Int
     */
    private fun getUserAge(): Int {
        return binding.seekBarAge.progress
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
            .setPositiveButton("登録", DialogInterface.OnClickListener { _, _ ->
                saveProfileData()
                changeFragment()
            })
            .setNegativeButton("キャンセル", DialogInterface.OnClickListener { _, _ ->
                // 何もしない
            })
            .show()
    }

    private fun saveProfileData() {
        viewModel.setUserSex(getGender(), requireContext())
        viewModel.setUserAge(getUserAge(), requireContext())
    }

    private fun changeFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, RegistrationProfileFragment()).commit()
    }
}