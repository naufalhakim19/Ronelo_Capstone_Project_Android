package com.example.ronelo.model

import androidx.lifecycle.ViewModel
import com.example.ronelo.Dummy

class MainModel : ViewModel() {
    fun returnNurse(): List<ModelNurse> = Dummy.dataNurse()
    fun returnNews(): List<ModelNews> = Dummy.dataNews()
    fun returnPatient(): List<ModelPatient> = Dummy.dataPatient()
}