package com.example.myexpert.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myexpert.R
import com.example.myexpert.activities.MainActivity
import com.example.myexpert.adapters.ChatMessageAdapter
import com.example.myexpert.databinding.FragmentChatBinding
import com.example.myexpert.models.ChatMessage
import com.example.myexpert.models.Choice
import com.example.myexpert.utils.Const
import com.example.myexpert.viewmodels.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChatViewModel by viewModels()

    private lateinit var adapter: ChatMessageAdapter
    private val messageData = mutableListOf<ChatMessage>()
    private var isInitQuestion = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt(EXPERT_NAME_ID_KEY)?.let {
            viewModel.expertNameId = it
            viewModel.expertName = getString(it)
        }
        arguments?.getInt(EXPERT_IMAGE_ID_KEY)?.let {
            viewModel.expertImageId = it
        }
        val chatId = arguments?.getString(CHAT_ID_KEY)
        isInitQuestion = chatId.isNullOrBlank()
        viewModel.initialize(requireContext(), chatId)

        // toolbar戻るボタンを表示
        (activity as MainActivity).toolBarCustomView.setBackButtonVisibility(View.VISIBLE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        initializeMessageList()
        initializeSendButton()
        // 履歴がある場合、復元
        restoreHistory()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.resetChatId()
    }

    /**
     * Chat履歴を復元
     */
    private fun restoreHistory() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getChatHistoryWithoutSystemRole().forEach { chat ->
                messageData.add(
                    ChatMessage(message = chat.content, role = chat.role)
                )
            }
            withContext(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
                binding.messageList.setSelection(messageData.size)
            }
        }
    }

    /**
     * MessageListを初期化
     */
    private fun initializeMessageList() {
        adapter = ChatMessageAdapter(requireContext(), messageData, viewModel.expertImageId)
        binding.messageList.adapter = adapter
    }

    /**
     * SendButtonにクリックリスナをセット
     */
    private fun initializeSendButton() {
        binding.sendButton.setOnClickListener {
            try {
                val inputText = getInputText()
                if (inputText.isNotEmpty()) {
                    binding.sendButton.isEnabled = false
                    binding.indicator.visibility = View.VISIBLE
                    addMessageListView(inputText)
                    lifecycleScope.launch(Dispatchers.IO) {
                        addMessageDatabase(inputText)
                        val choice = viewModel.generateText()
                        setChatGPTResponse(choice)
                        withContext(Dispatchers.Main) {
                            binding.sendButton.isEnabled = true
                            binding.indicator.visibility = View.GONE
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(tag, e.toString())
                binding.sendButton.isEnabled = true
                binding.indicator.visibility = View.GONE
            }
        }
    }

    /**
     * 入力値を取得して、入力欄を空にする
     */
    private fun getInputText(): String {
        val message = binding.messageInput.text.toString().trim()
        binding.messageInput.setText(R.string.empty)
        return message
    }

    /**
     * ChatGptから値を取得
     * @param choice
     */
    private suspend fun setChatGPTResponse(choice: Choice?) {
        if (choice == null) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "エラーが発生しました。", Toast.LENGTH_LONG).show()
            }
        } else {
            addMessageDatabase(choice.message.content, choice.message.role)
            withContext(Dispatchers.Main) {
                addMessageListView(choice.message.content, choice.message.role)
            }
        }
    }

    /**
     * Databaseにメッセージを追加
     */
    private suspend fun addMessageDatabase(message: String, role: String = Const.userRole) {
        if (isInitQuestion) {
            viewModel.insertChatThread(message)
            isInitQuestion = false
        }
        viewModel.insert(role, message)
    }

    /**
     * ListViewにメッセージを追加
     */
    private fun addMessageListView(message: String, role: String = Const.userRole) {
        messageData.add(ChatMessage(message, role))
        adapter.notifyDataSetChanged()
        binding.messageList.setSelection(messageData.size)
    }


    companion object {
        const val CHAT_ID_KEY = "CHAT_ID_KEY"
        const val EXPERT_IMAGE_ID_KEY = "EXPERT_IMAGE_ID_KEY"
        const val EXPERT_NAME_ID_KEY = "EXPERT_NAME_ID_KEY"
    }
}