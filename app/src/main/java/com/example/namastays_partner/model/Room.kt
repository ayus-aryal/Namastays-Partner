package com.example.namastays_partner.model

data class Room(
    val id: Int,
    val category: String,
    val maxGuests: Int,
    val bedType: String,
    val totalRooms: Int,
    val pricePerNight: Int
)