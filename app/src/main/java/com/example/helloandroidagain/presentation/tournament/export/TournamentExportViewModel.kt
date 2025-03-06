package com.example.helloandroidagain.presentation.tournament.export

import android.app.Application
import android.content.ContentValues
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.helloandroidagain.data.model.Tournament
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TournamentExportViewModel @Inject constructor(
    private val context: Application
) : ViewModel() {

    private val _saveImageStatus = MutableStateFlow<Boolean?>(null)
    val saveImageStatus: StateFlow<Boolean?> = _saveImageStatus.asStateFlow()

    fun saveImageToMediaStore(tournament: Tournament, bitmap: Bitmap) {
        val contentValues = ContentValues().apply {
            put(
                MediaStore.Images.Media.DISPLAY_NAME,
                "${tournament.name}_${tournament.id}.jpg"
            )
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/HelloAndroid")
        }

        val uri = context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )

        viewModelScope.launch(Dispatchers.IO) {
            val result: Boolean? = uri?.let {
                context.contentResolver.openOutputStream(it)?.use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }
            }
            _saveImageStatus.value = result
        }
    }
}