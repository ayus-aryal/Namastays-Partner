package com.example.namastays_partner.utilities

import android.net.Uri

enum class PhotoCategory {
    PROPERTY_FRONT,
    LOBBY,
    ROOM,
    AMENITIES,
    OTHER
}

data class PhotoCategoryState(
    val category: PhotoCategory,       // <-- new enum field
    val title: String,
    val description: String,
    val photos: List<Uri> = emptyList()
)