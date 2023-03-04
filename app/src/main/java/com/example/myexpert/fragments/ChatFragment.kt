package com.example.myexpert.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myexpert.R
import com.example.myexpert.adapters.ChatMessageAdapter
import com.example.myexpert.databinding.FragmentChatBinding
import com.example.myexpert.models.ChatMessage
import com.example.myexpert.repositories.ChatGDPRepository
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
    private var viewModel = ChatViewModel(ChatGDPRepository(), SharedPreferencesRepository(), ChatRepository(), ChatThreadRepository())

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
                messageData.addAll(
                    listOf(
                        // 質問
                        ChatMessage(chat.prompt, true),
                        // 回答
                        ChatMessage(chat.response, false)
                    )
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
                    addMessage(inputText, true)
                    lifecycleScope.launch(Dispatchers.IO) {
                        val response = getChatGPTResponse(inputText)
                        withContext(Dispatchers.Main) {
                            addMessage(response, false)
                            binding.sendButton.isEnabled = true
                        }
                    }
                } else {
                    binding.sendButton.isEnabled = true
                }
            } catch (e: Exception) {
                Log.e(tag, e.toString())
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
     */
    private suspend fun getChatGPTResponse(prompt: String): String {
        val response = viewModel.generateText(prompt = prompt)
        return if (response == null) {
            "エラー"
        } else {
            if (isInitQuestion) {
                viewModel.insertChatThread(prompt)
                isInitQuestion = false
            }
            viewModel.insert(prompt, response)
            response.text
        }
    }

    /**
     * ListViewにメッセージを追加
     */
    private fun addMessage(message: String, isMine: Boolean) {
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