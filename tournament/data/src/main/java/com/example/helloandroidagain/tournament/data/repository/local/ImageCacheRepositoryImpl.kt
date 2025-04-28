package com.example.helloandroidagain.tournament.data.repository.local

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.helloandroidagain.tournament.data.db.LogoDao
import com.example.helloandroidagain.tournament.domain.repository.ImageCacheRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class ImageCacheRepositoryImpl @Inject constructor(
    private val logoDao: LogoDao
) : ImageCacheRepository {

    override suspend fun saveImage(url: String, imageData: Bitmap) = withContext(Dispatchers.IO) {
        try {
            logoDao.updateCacheByThumbUrl(url, getByteArrayFromBitmap(imageData))
            Log.i(TAG, "Saving to cache $url")
        } catch (e: Exception) {
            Log.e(TAG, "Error while saving to cache $url", e)
            throw e
        }
    }

    override suspend fun loadImage(url: String): Bitmap? = withContext(Dispatchers.IO) {
        try {
            val byteArray = logoDao.getCachedLogoByThumbUrl(url)
            byteArray?.let { Log.i(TAG, "Loading from cache $url") }
                ?: Log.i(TAG, "Was not cached $url")
            byteArray?.let { BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size) }
        } catch (e: Exception) {
            Log.e(TAG, "Error while loading from cache $url", e)
            throw e
        }
    }

    private fun getByteArrayFromBitmap(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    companion object {
        private const val TAG = "ImageCacheService"
    }
}