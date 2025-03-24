package fakes

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.helloandroidagain.data.db.LogoDao
import com.example.helloandroidagain.domain.repository.ImageCacheRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class FakeImageCacheRepositoryImpl : ImageCacheRepository {
    override suspend fun saveImage(url: String, imageData: Bitmap) = 0
    override suspend fun loadImage(url: String): Bitmap? = null
}