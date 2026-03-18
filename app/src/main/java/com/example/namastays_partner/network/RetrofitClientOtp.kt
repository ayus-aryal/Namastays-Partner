package com.example.namastays_partner.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitClientOtp {

    private const val BASE_URL = "https://19b1-113-199-251-161.ngrok-free.app//"

    val api: AuthApi by lazy{

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }
}