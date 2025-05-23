package com.example.helloandroidagain.tournament.domain.repository

import android.graphics.Bitmap

interface ImageCacheRepository {
    suspend fun saveImage(url: String, imageData: Bitmap): Int
    suspend fun loadImage(url: String): Bitmap?
}