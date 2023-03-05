package com.example.myexpert.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myexpert.R
import com.example.myexpert.databinding.FragmentChatBinding
import com.example.myexpert.databinding.FragmentIntroductionBinding

class IntroductionFragment : Fragment() {
    private var _binding: FragmentIntroductionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntroductionBinding.inflate(inflater, container, false)
        binding.nextButton.setOnClickListener {
//            parentFragmentManager.beginTransaction().replace(R.id.frameLayout, )
        }
        return binding.root
    }
}