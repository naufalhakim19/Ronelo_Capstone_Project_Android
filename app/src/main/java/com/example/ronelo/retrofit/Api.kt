package com.example.ronelo.retrofit
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface Api {
    @Multipart
    @POST("predict")

    fun getDataPost(
        @Part file: MultipartBody.Part,
        @Part("composition") composition: RequestBody,
        @Part("contraindication") contraindication: RequestBody,
        @Part("description") description: RequestBody,
        @Part("dosage") dosage: RequestBody,
        @Part("how_to_use") how_to_use: RequestBody,
        @Part("name") name: RequestBody,
        @Part("side_effects") side_effects: RequestBody,
        @Part("warning") warning: RequestBody

    ): Call<CreatePostResponse>

    companion object{
        operator fun invoke():Api{
            return Retrofit.Builder()
                .baseUrl("http://34.126.144.187/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }
    }

}