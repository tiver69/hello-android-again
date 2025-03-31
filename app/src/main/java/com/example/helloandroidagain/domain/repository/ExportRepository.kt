package com.example.helloandroidagain.domain.repository

import android.graphics.Bitmap
import com.example.helloandroidagain.data.model.Tournament

interface ExportRepository {
    fun saveExportImage(tournament: Tournament, bitmap: Bitmap): Boolean
}