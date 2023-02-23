package com.example.chatgptapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chatgptapplication.databinding.FragmentSelectExpertBinding
import com.example.chatgptapplication.viewmodels.SelectExpertViewModel

class SelectExpertFragment : Fragment() {

    private lateinit var binding: FragmentSelectExpertBinding
    private val viewModel: SelectExpertViewModel = SelectExpertViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectExpertBinding.inflate(inflater, container, false)
        return binding.root
    }
}