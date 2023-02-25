package com.example.chatgptapplication.fragment

import android.os.Bundle
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

class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private var viewModel = ChatViewModel(ChatGDPRepository(), SharedPreferencesRepository(), ChatRepository())

    private lateinit var adapter: ChatMessageAdapter
    private val messageData = mutableListOf<ChatMessage>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        adapter = ChatMessageAdapter(requireContext(), messageData)
        binding.messageList.adapter = adapter

        binding.sendButton.setOnClickListener {
            val message = binding.messageInput.text.toString().trim()
            if (message.isNotEmpty()) {
                messageData.add(ChatMessage(message, true))
                adapter.notifyDataSetChanged()
                setChatGPTResponse(message)
                binding.messageInput.setText(R.string.empty)
            }
        }
        return binding.root
    }

    private fun setChatGPTResponse(prompt: String) {
        val apiKey = viewModel.getApiKey(requireContext())
        lifecycleScope.launch(Dispatchers.IO) {
            val response = viewModel.generateText(prompt = prompt, apiKey = apiKey)
            withContext(Dispatchers.Main) {
                if (response == null) {
                    messageData.add(ChatMessage("エラー", false))
                    adapter.notifyDataSetChanged()
                } else {
                    messageData.add(ChatMessage(response.text, false))
                    withContext(Dispatchers.IO) {
                        viewModel.insert(prompt, response)
                    }
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    companion object {
        const val EXPERT_IMAGE_ID_KEY = "EXPERT_IMAGE_ID_KEY"
        const val EXPERT_NAME_ID_KEY = "EXPERT_NAME_ID_KEY"
    }
}