package com.example.ronelo.consultMenu.display

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ronelo.consultMenu.model.ChatModel
import com.example.ronelo.consultMenu.repository.Repository
import com.google.cloud.dialogflow.v2.DetectIntentRequest
import com.google.cloud.dialogflow.v2.QueryInput
import com.google.cloud.dialogflow.v2.SessionsClient
import com.google.cloud.dialogflow.v2.TextInput
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ChatViewModel (app: Application, private val repo: Repository) : AndroidViewModel(app) {

    val chatList = repo.getChatList()

    private val TAG = "ChatViewModel"

    fun sendMessage(message: String, sessionName: String, sessionClient: SessionsClient?) {

        Log.e("TAG", "initTokenValue: $sessionClient")
        val queryInput = QueryInput.newBuilder()
            .setText(TextInput.newBuilder().setText(message).setLanguageCode("id")).build()

        try {
            GlobalScope.launch {
                val detectIntentRequest = DetectIntentRequest.newBuilder()
                    .setSession(sessionName)
                    .setQueryInput(queryInput)
                    .build()

                val response = sessionClient?.detectIntent(detectIntentRequest)
                val finalResponse = response?.queryResult?.fulfillmentText
                saveData(ChatModel(0, finalResponse.toString(), message))
            }
        } catch (e: Exception) {
            Log.e(TAG, "sendMessage: ${e.printStackTrace()}",)
        }

    }

    private fun saveData(chat: ChatModel) = viewModelScope.launch {
        repo.insertChatList(chat)
    }

    fun nukeChatList() = viewModelScope.launch {
        repo.nukeChatList()
    }

}