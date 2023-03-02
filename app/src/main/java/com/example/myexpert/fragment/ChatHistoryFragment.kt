package com.example.myexpert.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myexpert.adapters.ChatHistoryAdapter
import com.example.myexpert.adapters.ExpertAdapter
import com.example.myexpert.database.table.Chat
import com.example.myexpert.databinding.FragmentChatHistoryBinding
import com.example.myexpert.enums.Expert
import com.example.myexpert.repositories.ChatRepository
import com.example.myexpert.viewmodels.ChatHistoryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatHistoryFragment : Fragment() {
    private var _binding: FragmentChatHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChatHistoryViewModel = ChatHistoryViewModel(ChatRepository())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatHistoryBinding.inflate(layoutInflater, container, false)
        setRecyclerView()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setRecyclerView() {
        val recyclerView = binding.chatHistoryRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        lifecycleScope.launch(Dispatchers.IO) {
            val chatHistoryList = viewModel.getChatHistory()
            withContext(Dispatchers.Main) {
                val adapter = ChatHistoryAdapter(chatHistoryList, requireContext())
                adapter.itemClickListener = object : ChatHistoryAdapter.OnItemClickListener{
                    override fun onItemClick(chat: Chat) {
                    }
                }
                recyclerView.adapter = adapter
            }
        }
    }
}