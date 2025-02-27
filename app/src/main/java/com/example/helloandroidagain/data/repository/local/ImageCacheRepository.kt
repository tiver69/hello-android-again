package com.example.helloandroidagain.data.repository.local

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.helloandroidagain.data.db.LogoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class ImageCacheRepository @Inject constructor() {

    @Inject
    lateinit var logoDao: LogoDao

    suspend fun saveImage(url: String, imageData: Bitmap) = withContext(Dispatchers.IO) {
        try {
            logoDao.updateCacheByThumbUrl(url, getByteArrayFromBitmap(imageData))
            Log.i(TAG, "Saving to cache $url")
        } catch (e: Exception) {
            Log.e(TAG, "Error while saving to cache $url", e)
            throw e
        }
    }

    suspend fun loadImage(url: String): Bitmap? = withContext(Dispatchers.IO) {
        try {
            logoDao.getCachedLogoByThumbUrl(url)?.let { byteArray ->
                Log.i(TAG, "Loading from cache $url")
                BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            } ?: run {
                Log.i(TAG, "Was not cached $url")
                null
            }
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