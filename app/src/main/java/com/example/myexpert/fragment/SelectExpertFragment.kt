package com.example.myexpert.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myexpert.R
import com.example.myexpert.adapters.ExpertAdapter
import com.example.myexpert.databinding.FragmentSelectExpertBinding
import com.example.myexpert.enums.Expert
import com.example.myexpert.viewmodels.SelectExpertViewModel

class SelectExpertFragment : Fragment() {

    private var _binding: FragmentSelectExpertBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SelectExpertViewModel = SelectExpertViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectExpertBinding.inflate(inflater, container, false)
        setRecyclerView()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setRecyclerView() {
        val recyclerView = binding.expertRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = ExpertAdapter(requireContext())
        adapter.itemClickListener = object : ExpertAdapter.OnItemClickListener{
            override fun onItemClick(expert: Expert) {
                changeFragment(expert)
            }
        }
        recyclerView.adapter = adapter
    }

    private fun changeFragment(expert: Expert) {
        val fragment = ChatFragment()
        val args = Bundle().apply {
            putInt(ChatFragment.EXPERT_IMAGE_ID_KEY, expert.imageId)
            putInt(ChatFragment.EXPERT_NAME_ID_KEY, expert.titleId)
        }
        fragment.arguments = args
        parentFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .addToBackStack(null)
            .commit()
    }
}