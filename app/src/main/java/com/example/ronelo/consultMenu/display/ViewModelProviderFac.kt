package com.example.ronelo.consultMenu.display

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ronelo.consultMenu.repository.Repository

class ViewModelProviderFac(val app: Application, private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChatViewModel(app, repository) as T
    }
}