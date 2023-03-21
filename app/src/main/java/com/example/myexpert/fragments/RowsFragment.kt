package com.example.myexpert.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myexpert.R
import com.example.myexpert.adapters.ExpertAdapter
import com.example.myexpert.databinding.FragmentRowsBinding
import com.example.myexpert.enums.Expert
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RowsFragment : Fragment() {
    private var _binding: FragmentRowsBinding? = null
    private val binding get() = _binding!!
    private var expertClickedCallback: ((expert: Expert) -> Unit)? = null
    private var rowsData: Map<Int, List<Expert>>? = null

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

    fun setRowsData(rowsData: Map<Int, List<Expert>>) {
        this.rowsData = rowsData
    }

    private fun setRows() {
        rowsData?.forEach { titleId, expertList ->
            setRecyclerView(titleId, expertList)
        }
    }

    private fun setRecyclerView(titleId: Int, expertList: List<Expert>) {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.row, null, false)
        val expertCategory = view.findViewById<TextView>(R.id.expertCategory)
        val recyclerView = view.findViewById<RecyclerView>(R.id.expertRecyclerView)
        expertCategory.text = getString(titleId)
        // 固定サイズ
        recyclerView.setHasFixedSize(true)
        // 横Layout
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = linearLayoutManager
        // 初期position指定
        recyclerView.scrollToPosition(70)
        val adapter = ExpertAdapter(requireContext(), expertList)
        // ClickEventの指定
        adapter.itemClickListener = object : ExpertAdapter.OnItemClickListener{
            override fun onItemClick(expert: Expert) {
                expertClickedCallback?.invoke(expert)
            }
        }
        recyclerView.adapter = adapter

        binding.rowsParent.addView(view)
    }
}