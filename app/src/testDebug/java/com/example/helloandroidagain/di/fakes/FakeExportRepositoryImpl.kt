package com.example.helloandroidagain.di.fakes

import android.graphics.Bitmap
import com.example.helloandroidagain.tournament_domain.model.Tournament
import com.example.helloandroidagain.tournament_domain.repository.ExportRepository
import javax.inject.Inject

class FakeExportRepositoryImpl @Inject constructor() : ExportRepository {
    override fun saveExportImage(tournament: Tournament, bitmap: Bitmap): Boolean = true
}
