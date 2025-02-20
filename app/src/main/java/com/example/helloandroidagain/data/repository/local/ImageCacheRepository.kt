package com.example.helloandroidagain.data.repository.local

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.helloandroidagain.data.db.ImageCacheDatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class ImageCacheRepository @Inject constructor() {

    @Inject
    lateinit var databaseHelper: ImageCacheDatabaseHelper

    suspend fun saveImage(url: String, imageData: Bitmap) = withContext(Dispatchers.IO) {
        try {
            databaseHelper.writableDatabase.use { db ->
                val contentValues = ContentValues().apply {
                    put(ImageCacheDatabaseHelper.COLUMN_URL, url)
                    put(
                        ImageCacheDatabaseHelper.COLUMN_IMAGE,
                        getByteArrayFromBitmap(imageData)
                    )
                }
                db.insertWithOnConflict(
                    ImageCacheDatabaseHelper.TABLE_NAME,
                    null,
                    contentValues,
                    SQLiteDatabase.CONFLICT_REPLACE
                )
            }
            Log.i(TAG, "Saving to cache $url")
        } catch (e: Exception) {
            Log.e(TAG, "Error while saving to cache $url", e)
            throw e
        }
    }

    suspend fun loadImage(url: String): Bitmap? = withContext(Dispatchers.IO) {
        try {
            databaseHelper.readableDatabase.use { db ->
                val cursor = db.query(
                    ImageCacheDatabaseHelper.TABLE_NAME,
                    arrayOf(ImageCacheDatabaseHelper.COLUMN_IMAGE),
                    "${ImageCacheDatabaseHelper.COLUMN_URL} = ?",
                    arrayOf(url),
                    null,
                    null,
                    null
                )
                cursor.use {
                    if (it.moveToFirst()) {
                        val byteArray =
                            it.getBlob(
                                it.getColumnIndexOrThrow(ImageCacheDatabaseHelper.COLUMN_IMAGE)
                            )
                        Log.i(TAG, "Loading from cache $url")
                        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                    } else {
                        Log.i(TAG, "Was not cached $url")
                        null
                    }
                }
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