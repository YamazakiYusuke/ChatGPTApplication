package com.example.myexpert.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myexpert.databinding.FragmentApiKeyHintBinding
import com.example.myexpert.viewmodels.APIKeyHintViewModel

class ApiKeyHintFragment : Fragment() {
    private lateinit var binding: FragmentApiKeyHintBinding
    private lateinit var viewModel: APIKeyHintViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApiKeyHintBinding.inflate(inflater, container, false)
        return binding.root
    }
}