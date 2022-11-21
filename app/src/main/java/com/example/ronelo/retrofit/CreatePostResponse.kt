package com.example.ronelo.retrofit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreatePostResponse(

    val composition: String? = "",
    val contraindication: String? = "",
    val description: String? = "",
    val dosage: String? = "",
    val how_to_use: String? = "",
    val name: String? = "",
    val side_effects: String? = "",
    val warning: String? = "",

):Parcelable