package com.example.ronelo.model

import com.example.ronelo.Dummy

class DetailModel {
    private lateinit var id: String

    fun setDataNurse(nurseId: String) {
        this.id = nurseId
    }

    fun getDataNurse(): ModelNurse {
        lateinit var modelNurse: ModelNurse
        val nurse = Dummy.dataNurse()
        for (nurses in nurse) {
            if (nurses.id == id)
                modelNurse = nurses
        }
        return modelNurse
    }
}