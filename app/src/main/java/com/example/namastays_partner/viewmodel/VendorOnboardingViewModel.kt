package com.example.namastays_partner.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.namastays_partner.model.Room
import com.example.namastays_partner.network.RetrofitClient
import com.example.namastays_partner.network.VendorApi
import com.example.namastays_partner.utilities.Amenity
import com.example.namastays_partner.utilities.PhotoCategory
import com.example.namastays_partner.utilities.PhotoCategoryState
import com.example.namastays_partner.utilities.TokenManager
import com.example.namastays_partner.utilities.VendorOnboardingState
import com.example.namastays_partner.utilities.compressImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import kotlin.collections.filter
import kotlin.collections.map

class VendorOnboardingViewModel() : ViewModel(){



    var isLoading by mutableStateOf(false)
    private set

    var submissionSuccess by mutableStateOf(false)
    private set


    private val api = RetrofitClient.api

    var state by mutableStateOf(VendorOnboardingState())
        private set


    fun submitVendorOnboarding(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {

            try {
                isLoading = true
                submissionSuccess = false

                // Flatten all images into MultipartBody.Part
                val imageParts = state.photoCategories.flatMap { categoryState ->
                    categoryState.photos.mapNotNull { uri ->
                        compressImage(context, uri)?.let { file ->
                            fileToMultipart(file, "images") // MUST match backend @RequestPart("images")
                        }
                    }
                }

                // Prepare categories list in same order as images
                val categoryList = state.photoCategories.flatMap { categoryState ->
                    categoryState.photos.map { categoryState.category.name }
                }

                // Convert category list to JSON RequestBody
                val categoryRequestBody = com.google.gson.Gson().toJson(categoryList)
                    .toRequestBody("application/json; charset=utf-8".toMediaType())

                // Vendor JSON as RequestBody
                val vendorRequestBody = state.toJsonString()
                    .toRequestBody("application/json; charset=utf-8".toMediaType())

                // Call backend
                val response = api.onboardVendor(
                    vendorRequestBody,
                    imageParts,
                    categoryRequestBody
                )

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val body = response.body()

                        body?.let {
                            TokenManager.saveToken(context, it.token)
                            Log.d("TOKEN_DEBUG", "Saved token: ${it.token}")

                        }
                        submissionSuccess = true
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e("API_ERROR", response.errorBody()?.string() ?: "Unknown error")
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            } finally{
                withContext(Dispatchers.Main){
                    isLoading = false
                }
            }
        }
    }


    private fun fileToMultipart(file: File, partName: String): MultipartBody.Part {
        val requestFile = file.asRequestBody("image/jpeg".toMediaType())
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

    fun VendorOnboardingState.toJsonString(): String {
        // Using Gson
        return com.google.gson.Gson().toJson(this)
    }








    fun updateName(value: String){
        state = state.copy(name = value)
    }

    fun updatePhone(value: String){
        if(value.length <= 10)
            state = state.copy(phone = value)
    }

    fun updateEmail(value: String){
        state = state.copy(email = value)
    }

    fun updateAddress(value: String) {
        state = state.copy(
            address = state.address.copy(address = value)
        )
    }

    fun updateCity(value: String) {
        state = state.copy(
            address = state.address.copy(city = value)
        )
    }

    fun updateState(value: String) {
        state = state.copy(
            address = state.address.copy(state = value)
        )
    }

    fun updatePostalCode(value: String) {
        state = state.copy(
            address = state.address.copy(postalCode = value)
        )
    }

    fun updateCountry(value: String) {
        state = state.copy(
            address = state.address.copy(country = value)
        )
    }

    fun updatePropertyName(value: String){
        state = state.copy(propertyName = value)
    }

    fun updatePropertyType(value: String){
        state = state.copy(propertyType = value)
    }

    fun updatePropertyDescription(value: String){
        if(value.length <= 500) {
            state = state.copy(propertyDescription = value)
        }
    }

    fun updateYearEstablished(value: String){
        state = state.copy(yearEstablished = value)
    }

    fun addRoom(room: Room) {
        val newId = (state.rooms.maxOfOrNull { it.id } ?: 0) + 1

        state = state.copy(
            rooms = state.rooms + room.copy(id = newId)
        )
    }

    fun updateRoom(updatedRoom: Room){
        state = state.copy(rooms = state.rooms.map {
            if(it.id == updatedRoom.id)
                updatedRoom
            else it
        })
    }

    fun deleteRoom(roomId: Int){
        state = state.copy(rooms = state.rooms.filter { it.id != roomId })
    }

    fun toggleAmenity(amenity: Amenity) {
        val updated = if (state.amenities.any { it.id == amenity.id }) {
            state.amenities.filterNot { it.id == amenity.id }
        } else {
            state.amenities + amenity
        }

        state = state.copy(amenities = updated)
    }

    init {
        if (state.photoCategories.isEmpty()) {
            state = state.copy(
                photoCategories = listOf(
                    PhotoCategoryState(
                        category = PhotoCategory.PROPERTY_FRONT,

                        "Property Front Photo",
                        "Main entrance or building exterior"
                    ),
                    PhotoCategoryState(
                        category = PhotoCategory.LOBBY,

                        "Lobby / Reception",
                        "Reception or common areas"
                    ),
                    PhotoCategoryState(
                        category = PhotoCategory.ROOM,

                        "Room Photos",
                        "Bedrooms, bathroom and living area"
                    ),
                    PhotoCategoryState(
                        category = PhotoCategory.AMENITIES,

                        "Amenities Photos",
                        "Pool, gym, dining, or other facilities"
                    )
                )
            )
        }
    }

    fun addPhotos(categoryIndex: Int, uris: List<Uri>) {
        val updatedCategories = state.photoCategories.toMutableList()
        val category = updatedCategories[categoryIndex]

        updatedCategories[categoryIndex] = category.copy(
            photos = category.photos + uris
        )

        state = state.copy(photoCategories = updatedCategories)
    }

    fun removePhoto(categoryIndex: Int, uri: Uri) {
        val updatedCategories = state.photoCategories.toMutableList()
        val category = updatedCategories[categoryIndex]

        updatedCategories[categoryIndex] = category.copy(
            photos = category.photos - uri
        )

        state = state.copy(photoCategories = updatedCategories)
    }

    // ---- PRICING & POLICIES ----

    fun updateCheckInTime(value: String) {
        state = state.copy(
            policies = state.policies.copy(checkInTime = value)
        )
    }

    fun updateCheckOutTime(value: String) {
        state = state.copy(
            policies = state.policies.copy(checkOutTime = value)
        )
    }

    fun updateExtraGuestPrice(value: String) {
        state = state.copy(
            policies = state.policies.copy(extraGuestPrice = value)
        )
    }

    fun updateSmokingAllowed(value: Boolean) {
        state = state.copy(
            policies = state.policies.copy(smokingAllowed = value)
        )
    }

    fun updateChildrenAllowed(value: Boolean) {
        state = state.copy(
            policies = state.policies.copy(childrenAllowed = value)
        )
    }

    fun updatePetsAllowed(value: Boolean) {
        state = state.copy(
            policies = state.policies.copy(petsAllowed = value)
        )
    }

    fun updateBreakfastIncluded(value: Boolean) {
        state = state.copy(
            policies = state.policies.copy(breakfastIncluded = value)
        )
    }

    fun updateCancellationPolicy(value: String) {
        state = state.copy(
            policies = state.policies.copy(cancellationPolicy = value)
        )
    }
}