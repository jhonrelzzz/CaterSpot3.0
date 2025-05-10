package com.intprog32.caterspot30.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.intprog32.caterspot30.ChatActivity
import com.intprog32.caterspot30.Data.CatererChatHead
import com.intprog32.caterspot30.R

class CatererChatHeadAdapter(
    private val caterers: List<CatererChatHead>,
    private val context: Context
) : RecyclerView.Adapter<CatererChatHeadAdapter.CatererChatHeadViewHolder>() {

    class CatererChatHeadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage: ImageView = itemView.findViewById(R.id.caterer_profile_image)
        val catererName: TextView = itemView.findViewById(R.id.caterer_name)
        val lastMessage: TextView = itemView.findViewById(R.id.last_message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatererChatHeadViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.caterer_chat_head, parent, false)
        return CatererChatHeadViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatererChatHeadViewHolder, position: Int) {
        val caterer = caterers[position]
        holder.profileImage.setImageResource(caterer.profileImageResId)
        holder.catererName.text = caterer.catererName
        holder.lastMessage.text = caterer.lastMessage

        holder.itemView.setOnClickListener {
            // Handle click on chat head here
            val intent = Intent(context, ChatActivity::class.java)
            // You can pass caterer details to ChatActivity if needed
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = caterers.size
}