package jp.yusuke.myexpert.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jp.yusuke.myexpert.R
import jp.yusuke.myexpert.database.table.ChatThread

class ChatHistoryAdapter(
    private val chatThreadList: List<ChatThread>,
    private val context: Context
) :
    RecyclerView.Adapter<ChatHistoryAdapter.ViewHolder>() {

    var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(chatThread: ChatThread)
        fun onItemLongClick(chatThread: ChatThread)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val parentView: LinearLayout
        val chatTitle: TextView

        init {
            parentView = view.findViewById(R.id.chat_history_view)
            chatTitle = view.findViewById(R.id.chat_title)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.chat_history_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val chatThread = chatThreadList[position]
        viewHolder.chatTitle.text = truncateString(chatThread.init_question)
        viewHolder.parentView.setOnClickListener {
            itemClickListener?.onItemClick(chatThread)
        }
        viewHolder.parentView.setOnLongClickListener {
            itemClickListener?.onItemLongClick(chatThread)
            return@setOnLongClickListener true
        }
    }

    /**
     * 与えられた文字列が40文字以上の場合に、それ以降の文字列を省略記号 "..." に変換する
     * @param str
     * @return 変換後の文字列
     */
    private fun truncateString(str: String): String {
        return if (str.length > 40) {
            str.substring(0, 20) + "..."
        } else {
            str
        }
    }

    override fun getItemCount(): Int {
        return chatThreadList.size
    }
}