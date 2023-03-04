package com.example.myexpert.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.myexpert.R
import com.example.myexpert.models.ChatMessage

class ChatMessageAdapter(context: Context, messages: List<ChatMessage>, private val expertImageId: Int) :
    ArrayAdapter<ChatMessage>(context, 0, messages) {

    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.chat_message_item, parent, false)

        getItem(position)?.let { message ->
            val messageTextView = view.findViewById<TextView>(R.id.message_text)
            messageTextView.text = message.message

            val iconView = view.findViewById<ImageView>(R.id.icon_view)
            if (message.isMine) {
                // 自分のメッセージの場合
                val color = ContextCompat.getColor(context, R.color.dark_green)
                view.setBackgroundColor(color)
                iconView.setImageResource(R.drawable.default_icon)
            } else {
                // 相手のメッセージの場合
                val color = ContextCompat.getColor(context, R.color.green)
                view.setBackgroundColor(color)
                iconView.setImageResource(expertImageId)
            }
        }
        return view
    }
}
