package com.example.helloandroidagain.service

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.helloandroidagain.db.ImageCacheDatabaseHelper
import java.io.ByteArrayOutputStream

class ImageCacheService(context: Context) {
    private val database: SQLiteDatabase = ImageCacheDatabaseHelper(context).writableDatabase

    fun saveImage(url: String, imageData: Bitmap) {
        Log.i(TAG, "Saving to cache $url")
        val contentValues = ContentValues().apply {
            put(ImageCacheDatabaseHelper.COLUMN_URL, url)
            put(ImageCacheDatabaseHelper.COLUMN_IMAGE, getByteArrayFromBitmap(imageData))
        }
        database.insertWithOnConflict(
            ImageCacheDatabaseHelper.TABLE_NAME,
            null,
            contentValues,
            SQLiteDatabase.CONFLICT_REPLACE
        )
    }

    fun loadImage(url: String): Bitmap? {
        val cursor = database.query(
            ImageCacheDatabaseHelper.TABLE_NAME,
            arrayOf(ImageCacheDatabaseHelper.COLUMN_IMAGE),
            "${ImageCacheDatabaseHelper.COLUMN_URL} = ?",
            arrayOf(url),
            null, null, null
        )
        return if (cursor.moveToFirst()) {
            val byteArray =
                cursor.getBlob(cursor.getColumnIndexOrThrow(ImageCacheDatabaseHelper.COLUMN_IMAGE))
            Log.i(TAG, "Loading from cache $url")
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        } else {
            Log.i(TAG, "Was not cached $url")
            null
        }.also { cursor.close() }
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