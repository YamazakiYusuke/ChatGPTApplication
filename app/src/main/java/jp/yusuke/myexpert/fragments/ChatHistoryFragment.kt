package jp.yusuke.myexpert.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import jp.yusuke.myexpert.R
import jp.yusuke.myexpert.activities.MainActivity
import jp.yusuke.myexpert.adapters.ChatHistoryAdapter
import jp.yusuke.myexpert.database.table.ChatThread
import jp.yusuke.myexpert.databinding.FragmentChatHistoryBinding
import jp.yusuke.myexpert.viewmodels.ChatHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ChatHistoryFragment : Fragment() {
    private var _binding: FragmentChatHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChatHistoryViewModel by viewModels()

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

    override fun onResume() {
        super.onResume()
        // toolbar戻るボタンを非表示
        (activity as MainActivity).toolBarCustomView.setBackButtonVisibility(View.INVISIBLE)
    }

    override fun onPause() {
        super.onPause()
        chatThreadList = mutableListOf()
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
            .setMessage(
                """
                チャット: ${chatThread.init_question}
            """.trimIndent()
            )
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