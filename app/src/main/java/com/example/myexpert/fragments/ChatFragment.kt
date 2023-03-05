package com.example.myexpert.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myexpert.R
import com.example.myexpert.adapters.ChatMessageAdapter
import com.example.myexpert.databinding.FragmentChatBinding
import com.example.myexpert.models.ChatMessage
import com.example.myexpert.models.Choice
import com.example.myexpert.models.Message
import com.example.myexpert.repositories.ChatGPTRepository
import com.example.myexpert.repositories.ChatRepository
import com.example.myexpert.repositories.ChatThreadRepository
import com.example.myexpert.repositories.SharedPreferencesRepository
import com.example.myexpert.viewmodels.ChatViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private var viewModel = ChatViewModel(ChatGPTRepository(), SharedPreferencesRepository(), ChatRepository(), ChatThreadRepository())

    private lateinit var adapter: ChatMessageAdapter
    private val messageData = mutableListOf<ChatMessage>()

    private var isInitQuestion = true

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
        viewModel.initialize(requireContext(), chatId)
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
            viewModel.getChatHistory().forEach { chat ->
                messageData.add(
                    ChatMessage(message = chat.content, isMine = chat.role == "user")
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
                binding.sendButton.isEnabled = false
                val inputText = getInputText()
                if (inputText.isNotEmpty()) {
                    addMessageListView(inputText)
                    lifecycleScope.launch(Dispatchers.IO) {
                        addMessageDatabase(inputText)
                        val choice = viewModel.generateText()
                        setChatGPTResponse(choice)
                    }
                }
            } catch (e: Exception) {
                Log.e(tag, e.toString())
            } finally {
                binding.sendButton.isEnabled = true
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
    private suspend fun addMessageDatabase(message: String, role: String = "user") {
        if (isInitQuestion) {
            viewModel.insertChatThread(message)
            isInitQuestion = false
        }
        viewModel.insert(role, message)
    }

    /**
     * ListViewにメッセージを追加
     */
    private fun addMessageListView(message: String, role: String = "user") {
        val isMine = role == "user"
        messageData.add(ChatMessage(message, isMine))
        adapter.notifyDataSetChanged()
        binding.messageList.setSelection(messageData.size)
    }


    companion object {
        const val CHAT_ID_KEY = "CHAT_ID_KEY"
        const val EXPERT_IMAGE_ID_KEY = "EXPERT_IMAGE_ID_KEY"
        const val EXPERT_NAME_ID_KEY = "EXPERT_NAME_ID_KEY"
    }
}