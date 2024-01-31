package jp.yusuke.myexpert.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import jp.yusuke.myexpert.databinding.FragmentApiKeyHintBinding
import jp.yusuke.myexpert.viewmodels.APIKeyHintViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApiKeyHintFragment : Fragment() {
    private var _binding: FragmentApiKeyHintBinding? = null
    private val binding get() = _binding!!
    private val viewModel: APIKeyHintViewModel by viewModels()

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