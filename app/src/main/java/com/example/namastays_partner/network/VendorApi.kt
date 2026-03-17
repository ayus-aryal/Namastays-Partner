package com.example.namastays_partner.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.Response
import java.util.UUID


interface VendorApi {
    @Multipart
    @POST("/api/v1/vendors/onboard")
    suspend fun onboardVendor(
        @Part("vendorData") vendorData: RequestBody,
        @Part images: List<MultipartBody.Part>,      // multiple images
        @Part("categories") categories: RequestBody // send as JSON array
    ): Response<UUID>
}