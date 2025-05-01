package com.intprog32.caterspot30.Adapters

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.intprog32.caterspot30.Data.Message
import com.intprog32.caterspot30.R

class ChatAdapter(private val messages: List<Message>) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val messageText: TextView = view.findViewById(R.id.message_text)
        val container: LinearLayout = view as LinearLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = messages[position]
        holder.messageText.text = message.messageText

        // Dynamic alignment and background
        val layoutParams = holder.messageText.layoutParams as LinearLayout.LayoutParams
        layoutParams.gravity = if (message.isSentByUser) Gravity.END else Gravity.START
        holder.messageText.layoutParams = layoutParams

        holder.messageText.setBackgroundResource(
            if (message.isSentByUser) R.drawable.bg_user_msg else R.drawable.bg_other_msg
        )
    }

    override fun getItemCount(): Int = messages.size
}
