package com.example.helloandroidagain.tournament_data.repository.local

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.provider.MediaStore
import com.example.helloandroidagain.tournament_domain.model.Tournament
import com.example.helloandroidagain.tournament_domain.repository.ExportRepository
import javax.inject.Inject

class ExportRepositoryImpl @Inject constructor(
    private val contentResolver: ContentResolver
) : ExportRepository {

    override fun saveExportImage(tournament: Tournament, bitmap: Bitmap): Boolean {
        val contentValues = ContentValues().apply {
            put(
                MediaStore.Images.Media.DISPLAY_NAME,
                "${tournament.name}_${tournament.id}.jpg"
            )
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/HelloAndroid")
        }

        val uri = contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ) ?: return false

        return contentResolver.openOutputStream(uri)?.use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        } ?: false
    }
}
