package com.example.helloandroidagain.di.fakes

import android.graphics.Bitmap
import com.example.helloandroidagain.tournament.domain.repository.ImageCacheRepository

class FakeImageCacheRepositoryImpl : ImageCacheRepository {
    override suspend fun saveImage(url: String, imageData: Bitmap) = 0
    override suspend fun loadImage(url: String): Bitmap? = null
}