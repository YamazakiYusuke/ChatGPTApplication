package com.example.chatgptapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatgptapplication.R
import com.example.chatgptapplication.enums.Expert


class ExpertAdapter(private val context: Context) :
    RecyclerView.Adapter<ExpertAdapter.ViewHolder>() {

    private val expertList = Expert.values()

    var itemClickListener: OnItemClickListener? = null
    interface OnItemClickListener {
        fun onItemClick(expert: Expert)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView
        val expertImage: ImageView
        val expertTitle: TextView

        init {
            cardView = view.findViewById(R.id.expertCardView)
            expertImage = view.findViewById(R.id.expertImage)
            expertTitle = view.findViewById(R.id.expertTitle)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.expert_card_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val expert = expertList[position]
        viewHolder.expertImage.setImageResource(expert.imageId)
        viewHolder.expertTitle.text = context.getString(expert.titleId)
        viewHolder.cardView.setOnClickListener {
            itemClickListener?.onItemClick(expert)
        }
    }

    override fun getItemCount(): Int {
        return expertList.size
    }
}