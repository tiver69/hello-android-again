package com.example.helloandroidagain.tournament_domain.repository

import android.graphics.Bitmap
import com.example.helloandroidagain.tournament_domain.model.Tournament

interface ExportRepository {
    fun saveExportImage(tournament: Tournament, bitmap: Bitmap): Boolean
}