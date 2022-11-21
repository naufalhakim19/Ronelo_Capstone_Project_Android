package com.example.ronelo.consultMenu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ronelo.consultMenu.model.ChatModel
import com.example.ronelo.databinding.ItemChatBinding

class AdapterChat : RecyclerView.Adapter<AdapterChat.ViewHolder>(){
    private var chatList = ArrayList<ChatModel>()

    fun setData(items: List<ChatModel>) {
        chatList.clear() //clear the old list before initiate a new list
        chatList.addAll(items) // adding new data list to array
        notifyDataSetChanged() // notify the adapter to refresh the data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterChat.ViewHolder {
        val view = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterChat.ViewHolder, position: Int) {
        holder.bind(chatList[position])
    }

    override fun getItemCount(): Int = chatList.size

    inner class ViewHolder(private val binding : ItemChatBinding ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: ChatModel) {
            binding.tvMessageOutcoming.text = chat.msgUser
            binding.tvMessageIncoming.text = chat.responseAgent
        }
    }
}