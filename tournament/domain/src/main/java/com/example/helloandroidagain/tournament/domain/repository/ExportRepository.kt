package com.example.helloandroidagain.tournament.domain.repository

import android.graphics.Bitmap
import com.example.helloandroidagain.tournament.domain.model.Tournament

interface ExportRepository {
    suspend fun saveExportImage(tournament: Tournament, bitmap: Bitmap): Boolean
}