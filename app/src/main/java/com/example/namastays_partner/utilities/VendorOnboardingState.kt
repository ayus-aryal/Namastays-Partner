package com.example.namastays_partner.utilities

import com.example.namastays_partner.model.AddressRequest
import com.example.namastays_partner.model.PolicyRequest
import com.example.namastays_partner.model.Room

data class VendorOnboardingState(
    val name: String = "",
    val phone: String = "",
    val email: String = "",

    val address: AddressRequest = AddressRequest(),

    val propertyName: String = "",
    val propertyType: String = "",
    val propertyDescription: String = "",
    val yearEstablished: String = "",

    val rooms: List<Room> = emptyList(),

    val amenities: List<Amenity> = emptyList(),

    val photoCategories: List<PhotoCategoryState> = emptyList(),

    val policies: PolicyRequest = PolicyRequest()
)
