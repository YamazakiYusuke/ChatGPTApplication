package com.example.myexpert.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myexpert.R
import com.example.myexpert.adapters.ChatHistoryAdapter
import com.example.myexpert.adapters.ChatMessageAdapter
import com.example.myexpert.database.table.ChatThread
import com.example.myexpert.databinding.FragmentChatHistoryBinding
import com.example.myexpert.repositories.ChatRepository
import com.example.myexpert.repositories.ChatThreadRepository
import com.example.myexpert.viewmodels.ChatHistoryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ChatHistoryFragment : Fragment() {
    private var _binding: FragmentChatHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChatHistoryViewModel = ChatHistoryViewModel(ChatThreadRepository(), ChatRepository())

    private lateinit var adapter: ChatHistoryAdapter
    var chatThreadList: MutableList<ChatThread> = mutableListOf()

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
            chatThreadList.addAll(viewModel.getChatHistory())
            withContext(Dispatchers.Main) {
                adapter = ChatHistoryAdapter(chatThreadList, requireContext())
                adapter.itemClickListener = object : ChatHistoryAdapter.OnItemClickListener {
                    override fun onItemClick(chatThred: ChatThread) {
                        changeFragment(chatThred)
                    }
                    override fun onItemLongClick(chatThread: ChatThread) {
                        removeChatThread(chatThread)
                    }
                }
                recyclerView.adapter = adapter
            }
        }
    }

    private fun changeFragment(chatThread: ChatThread) {
        val fragment = ChatFragment()
        val args = Bundle().apply {
            putString(ChatFragment.CHAT_ID_KEY, chatThread.chat_id)
            putInt(ChatFragment.EXPERT_IMAGE_ID_KEY, chatThread.expert_image_id)
            putInt(ChatFragment.EXPERT_NAME_ID_KEY, chatThread.expert_name_id)
        }
        fragment.arguments = args
        parentFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .addToBackStack(null)
            .commit()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeChatThread(chatThread: ChatThread) {
        AlertDialog.Builder(context)
            .setTitle(R.string.are_you_sure_delete)
            .setMessage("""
                チャット: ${chatThread.init_question}
            """.trimIndent())
            .setPositiveButton(R.string.yes) { _, _ ->
                lifecycleScope.launch(Dispatchers.IO) {
                    // ChatThread削除
                    viewModel.deleteChat(chatThread)
                    // recyclerViewに反映
                    withContext(Dispatchers.Main) {
                        chatThreadList.remove(chatThread)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
            .setNegativeButton(R.string.no) { _, _ ->
            }
            .show()
    }
}