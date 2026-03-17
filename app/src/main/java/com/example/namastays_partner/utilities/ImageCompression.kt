package com.example.namastays_partner.utilities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import androidx.core.graphics.scale
import java.io.File

suspend fun compressImage(context: Context, uri: Uri): File? = withContext(Dispatchers.IO) {
    try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()

        val maxWidth = 1280
        val ratio = bitmap.height.toFloat() / bitmap.width
        val newHeight = (maxWidth * ratio).toInt()
        val resizedBitmap = bitmap.scale(maxWidth, newHeight)

        val file = File(context.cacheDir, "compressed_${System.currentTimeMillis()}.jpg")
        val outputStream = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        file.writeBytes(outputStream.toByteArray())

        bitmap.recycle()
        resizedBitmap.recycle()
        file
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}