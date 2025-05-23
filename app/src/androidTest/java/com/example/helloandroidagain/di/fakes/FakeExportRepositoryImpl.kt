package com.example.helloandroidagain.di.fakes

import android.graphics.Bitmap
import com.example.helloandroidagain.tournament.domain.model.Tournament
import com.example.helloandroidagain.tournament.domain.repository.ExportRepository
import javax.inject.Inject

class FakeExportRepositoryImpl @Inject constructor() : ExportRepository {
    override suspend fun saveExportImage(tournament: Tournament, bitmap: Bitmap): Boolean = true
}
