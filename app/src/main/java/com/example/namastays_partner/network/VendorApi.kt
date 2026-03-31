package com.example.namastays_partner.network

import com.example.namastays_partner.model.OnboardingResponse
import com.example.namastays_partner.model.VendorStatusResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.util.UUID


interface VendorApi {
    @Multipart
    @POST("/api/v1/vendors/onboard")
    suspend fun onboardVendor(
        @Part("vendorData") vendorData: RequestBody,
        @Part images: List<MultipartBody.Part>,      // multiple images
        @Part("categories") categories: RequestBody // send as JSON array
    ): Response<OnboardingResponse>


    @GET("/vendor/me")
    suspend fun getVendorStatus(
        @Header("Authorization") token: String
    ): Response<VendorStatusResponse>

    @GET("/auth/check-user")
    suspend fun checkUser(
        @Query("phone") phone: String
    ): Response<Boolean>

    @GET("/vendor/status-by-phone")
    suspend fun getVendorStatusByPhone(
        @Query("phone") phone: String
    ): Response<VendorStatusResponse>
}