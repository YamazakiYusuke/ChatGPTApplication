package com.example.chatgptapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatgptapplication.adapters.ExpertAdapter
import com.example.chatgptapplication.databinding.FragmentSelectExpertBinding
import com.example.chatgptapplication.enums.Expert
import com.example.chatgptapplication.viewmodels.SelectExpertViewModel

class SelectExpertFragment : Fragment() {

    private lateinit var binding: FragmentSelectExpertBinding
    private val viewModel: SelectExpertViewModel = SelectExpertViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectExpertBinding.inflate(inflater, container, false)
        setRecyclerView()
        return binding.root
    }

    private fun setRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = ExpertAdapter(requireContext())
        adapter.itemClickListener = object : ExpertAdapter.OnItemClickListener{
            override fun onItemClick(expert: Expert) {

            }
        }
        recyclerView.adapter = adapter
    }
}