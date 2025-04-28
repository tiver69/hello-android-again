package com.example.helloandroidagain.di.fakes

import android.graphics.Bitmap
import com.example.helloandroidagain.tournament_domain.repository.ImageCacheRepository
import javax.inject.Inject

class FakeImageCacheRepositoryImpl @Inject constructor() : ImageCacheRepository {
    override suspend fun saveImage(url: String, imageData: Bitmap) = 0
    override suspend fun loadImage(url: String): Bitmap? = null
}