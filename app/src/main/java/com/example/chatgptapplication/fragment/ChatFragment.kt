package com.example.chatgptapplication.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.chatgptapplication.R
import com.example.chatgptapplication.adapters.ChatMessageAdapter
import com.example.chatgptapplication.databinding.FragmentChatBinding
import com.example.chatgptapplication.model.ChatMessage
import com.example.chatgptapplication.repositories.ChatGDPRepository
import com.example.chatgptapplication.repositories.ChatRepository
import com.example.chatgptapplication.repositories.SharedPreferencesRepository
import com.example.chatgptapplication.viewmodels.ChatViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private var viewModel = ChatViewModel(ChatGDPRepository(), SharedPreferencesRepository(), ChatRepository())
    private var expertImageId: Int = 0

    private lateinit var adapter: ChatMessageAdapter
    private val messageData = mutableListOf<ChatMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt(EXPERT_NAME_ID_KEY)?.let {
            viewModel.expertName = getString(it)
        }
        arguments?.getInt(EXPERT_IMAGE_ID_KEY)?.let {
            expertImageId = it
        }
        viewModel.initialize(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        initializeMessageList()
        initializeSendButton()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.resetChatId()
    }

    /**
     * MessageListを初期化
     */
    private fun initializeMessageList() {
        adapter = ChatMessageAdapter(requireContext(), messageData, expertImageId)
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
        const val EXPERT_IMAGE_ID_KEY = "EXPERT_IMAGE_ID_KEY"
        const val EXPERT_NAME_ID_KEY = "EXPERT_NAME_ID_KEY"
    }
}