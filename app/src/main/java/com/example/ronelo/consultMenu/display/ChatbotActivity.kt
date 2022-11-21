package com.example.ronelo.consultMenu.display

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ronelo.R
import com.example.ronelo.consultMenu.adapter.AdapterChat
import com.example.ronelo.consultMenu.adapter.AdapterSuggestion
import com.example.ronelo.consultMenu.db.ChatDatabase
import com.example.ronelo.consultMenu.repository.Repository
import com.example.ronelo.consultMenu.util.Helper
import com.example.ronelo.databinding.ActivityChatbotBinding
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.auth.oauth2.ServiceAccountCredentials
import com.google.cloud.dialogflow.v2.SessionName
import com.google.cloud.dialogflow.v2.SessionsClient
import com.google.cloud.dialogflow.v2.SessionsSettings
import java.io.InputStream
import java.util.*

class ChatbotActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatbotBinding
    private lateinit var viewModel: ChatViewModel
    private lateinit var chatAdapter: AdapterChat
    private lateinit var adapterSuggestion: AdapterSuggestion

    private var sessionName: SessionName? = null
    private var sessionClient: SessionsClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatbotBinding.inflate(layoutInflater)
        setContentView(binding.root)
        chatAdapter = AdapterChat() //initiate adapter
        adapterSuggestion = AdapterSuggestion()
        setupAdapter()
        initViewModel()
        initSuggestionList()
        initSession()
        setupChat()
        onClickHandler()
    }

    private fun initSuggestionList() {

        binding.rvSuggestionChat.apply {
            layoutManager =
                LinearLayoutManager(this@ChatbotActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterSuggestion
        }

        val suggestionList = resources.getStringArray(R.array.suggestion_list)

        adapterSuggestion.setSuggestionData(suggestionList)
    }

    private fun setupAdapter() {
        binding.rvChat.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(this@ChatbotActivity).apply {
                stackFromEnd = true
            }
        }
    }

    private fun onClickHandler() {

        binding.btnSend.setOnClickListener {
            val messageText = binding.etMessage.text.toString().trim()
            sendMessage(messageText)
        }

        binding.btnClearChat.setOnClickListener {
            nukeChatList()
        }

        binding.etMessage.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                val messageText = binding.etMessage.text.toString().trim()
                sendMessage(messageText)
                true
            } else {
                false
            }
        }

        adapterSuggestion.setOnItemClickListener { message ->
            sendMessage(message)
        }

    }

    private fun initViewModel() {
        val repository = Repository(ChatDatabase(this))
        val viewModelProviderFactory = ViewModelProviderFac(application, repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(ChatViewModel::class.java)
    }

    private fun initSession() {
        val res: InputStream = resources.openRawResource(R.raw.mantau)
        val credentials = GoogleCredentials.fromStream(res)
        val projectId = (credentials as ServiceAccountCredentials).projectId

        val settingsBuilder = SessionsSettings.newBuilder()
        val sessionsSettings =
            settingsBuilder.setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build()

        sessionClient = SessionsClient.create(sessionsSettings)
        sessionName = SessionName.of(projectId, UUID.randomUUID().toString())
    }

    private fun setupChat() {
        viewModel.chatList.observe(this, { chatList ->
            chatAdapter.setData(chatList)
            scrollToBottom()
        })
    }

    private fun sendMessage(messageText: String) {
        if (!TextUtils.isEmpty(messageText)) {
            viewModel.sendMessage(messageText, sessionName.toString(), sessionClient)
            Helper.closeKeyboard(this, binding.root) //close the keyboard
            binding.etMessage.setText("") // clear current text in edit text
            scrollToBottom() //scroll to bottom after sending message
        } else {
            Toast.makeText(this, resources.getString(R.string.empty_message), Toast.LENGTH_SHORT).show()
        }
    }

    private fun nukeChatList() {
        viewModel.nukeChatList()
    }

    private fun scrollToBottom() {
        if (chatAdapter.itemCount > 0) {
            binding.rvChat.scrollToPosition(chatAdapter.itemCount - 1)
        }
    }
}