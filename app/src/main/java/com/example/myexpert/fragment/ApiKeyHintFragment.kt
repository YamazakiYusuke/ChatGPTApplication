package com.example.myexpert.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myexpert.databinding.FragmentApiKeyHintBinding
import com.example.myexpert.viewmodels.APIKeyHintViewModel

class ApiKeyHintFragment : Fragment() {
    private var _binding: FragmentApiKeyHintBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: APIKeyHintViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApiKeyHintBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}