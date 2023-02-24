package com.example.chatgptapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.chatgptapplication.R
import com.example.chatgptapplication.model.ChatMessage

class ChatMessageAdapter(context: Context, messages: List<ChatMessage>) :
    ArrayAdapter<ChatMessage>(context, 0, messages) {

    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.chat_message_item, parent, false)

        getItem(position)?.let { message ->
            val messageTextView = view.findViewById<TextView>(R.id.message_text)
            messageTextView.text = message.message

            val iconView = view.findViewById<ImageView>(R.id.icon_view)
            if (message.isMine) {
                // 自分のメッセージの場合は右側に表示
                messageTextView.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                iconView.visibility = View.GONE
            } else {
                // 相手のメッセージの場合は左側に表示
                messageTextView.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                iconView.visibility = View.GONE
            }
        }
        return view
    }
}
