package com.example.helloandroidagain.data.repository.local

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.helloandroidagain.data.db.ImageCacheDatabaseHelper
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class ImageCacheRepository @Inject constructor() {

    @Inject
    lateinit var databaseHelper: ImageCacheDatabaseHelper

    fun saveImage(url: String, imageData: Bitmap): Completable =
        Completable.create { emitter ->
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
                emitter.onComplete()
            } catch (e: Exception) {
                Log.e(TAG, "Error while saving to cache $url", e)
                emitter.onError(e)
            }
        }.subscribeOn(Schedulers.io())

    fun loadImage(url: String): Maybe<Bitmap> =
        Maybe.create { emitter ->
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
                                it.getBlob(it.getColumnIndexOrThrow(ImageCacheDatabaseHelper.COLUMN_IMAGE))
                            Log.i(TAG, "Loading from cache $url")
                            val bitmapResult =
                                BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                            emitter.onSuccess(bitmapResult)
                        } else {
                            Log.i(TAG, "Was not cached $url")
                            emitter.onComplete()
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error while loading from cache $url", e)
                emitter.onError(e)
            }
        }.subscribeOn(Schedulers.io())

    private fun getByteArrayFromBitmap(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    companion object {
        private const val TAG = "ImageCacheService"
    }
}