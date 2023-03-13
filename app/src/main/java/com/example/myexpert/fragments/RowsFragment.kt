package com.example.myexpert.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myexpert.R
import com.example.myexpert.adapters.ExpertAdapter
import com.example.myexpert.databinding.FragmentRowsBinding
import com.example.myexpert.enums.Expert

class RowsFragment : Fragment() {
    private var _binding: FragmentRowsBinding? = null
    private val binding get() = _binding!!
    private var expertClickedCallback: ((expert: Expert) -> Unit)? = null
    private var rowsData: ArrayList<Array<Expert>>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRows()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setExpertClickedCallback(expertClickedCallback: ((expert: Expert) -> Unit)) {
        this.expertClickedCallback = expertClickedCallback
    }

    fun setRowsData(rowsData: ArrayList<Array<Expert>>) {
        this.rowsData = rowsData
    }

    private fun setRows() {
        rowsData?.forEach {
            setRecyclerView(it)
        }
    }

    private fun setRecyclerView(expertList: Array<Expert>) {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.row, binding.rowsParent, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.expertRecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = linearLayoutManager
        val adapter = ExpertAdapter(requireContext(), expertList)
        adapter.itemClickListener = object : ExpertAdapter.OnItemClickListener{
            override fun onItemClick(expert: Expert) {
                expertClickedCallback?.invoke(expert)
            }
        }
        recyclerView.adapter = adapter

        binding.rowsParent.addView(view)
    }
}