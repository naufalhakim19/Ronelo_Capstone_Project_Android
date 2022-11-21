package com.example.ronelo.consultMenu.repository

import com.example.ronelo.consultMenu.db.ChatDatabase
import com.example.ronelo.consultMenu.model.ChatModel

class Repository (val db: ChatDatabase) {

    suspend fun insertChatList(chat: ChatModel) = db.chatDao().insert(chat)

    suspend fun nukeChatList() = db.chatDao().nukeChatList()

    fun getChatList() = db.chatDao().getListChat()

}