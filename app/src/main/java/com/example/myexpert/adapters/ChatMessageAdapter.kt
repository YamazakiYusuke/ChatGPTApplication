package com.example.myexpert.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.myexpert.R
import com.example.myexpert.models.ChatMessage
import com.example.myexpert.utils.Utils
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.core.MarkwonTheme


class ChatMessageAdapter(context: Context, messages: List<ChatMessage>, private val expertImageId: Int) :
    ArrayAdapter<ChatMessage>(context, 0, messages) {

    private val inflater = LayoutInflater.from(context)
    private val markwon = Markwon.builder(context)
        .usePlugin(object : AbstractMarkwonPlugin() {
            override fun configureTheme(builder: MarkwonTheme.Builder) {
                builder
                    .codeTextColor(Color.WHITE)
                    .codeBackgroundColor(Color.BLACK)
            }
        })
        .build()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.chat_message_item, parent, false)

        getItem(position)?.let { message ->
            val messageTextView = view.findViewById<TextView>(R.id.message_text)

            val iconView = view.findViewById<ImageView>(R.id.icon_view)
            if (Utils().isUserRole(message.role)) {
                // 自分のメッセージの場合
                messageTextView.text = message.message
                val color = ContextCompat.getColor(context, R.color.white)
                view.setBackgroundColor(color)
                iconView.setImageResource(R.drawable.default_icon)
            } else if (Utils().isAssistantRole(message.role)) {
                // 相手のメッセージの場合
                setTextAsMarkdown(message.message, messageTextView)
                val color = ContextCompat.getColor(context, R.color.chat_message_gb)
                view.setBackgroundColor(color)
                iconView.setImageResource(expertImageId)
            }
        }
        return view
    }

    private fun setTextAsMarkdown(text: String, textView: TextView) {
        val node = markwon.parse(text)
        val markdown = markwon.render(node)
        markwon.setParsedMarkdown(textView, markdown)
    }
}
