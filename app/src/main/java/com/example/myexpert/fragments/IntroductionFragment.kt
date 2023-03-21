package com.example.myexpert.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myexpert.R
import com.example.myexpert.databinding.FragmentIntroductionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroductionFragment : Fragment() {
    private var _binding: FragmentIntroductionBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntroductionBinding.inflate(inflater, container, false)
        binding.startButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, SetApiKeyFragment())
                .commit()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}