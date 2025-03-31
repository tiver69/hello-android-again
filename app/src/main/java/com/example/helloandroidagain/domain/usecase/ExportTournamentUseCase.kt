package com.example.helloandroidagain.domain.usecase

import android.graphics.Bitmap
import com.example.helloandroidagain.data.model.Tournament
import com.example.helloandroidagain.domain.repository.ExportRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExportTournamentUseCase @Inject constructor(
    private val repository: ExportRepository
) {
    suspend fun invoke(tournament: Tournament, bitmap: Bitmap): Boolean =
        withContext(Dispatchers.IO) {
            repository.saveExportImage(tournament, bitmap)
        }
}