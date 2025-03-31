package fakes

import android.graphics.Bitmap
import com.example.helloandroidagain.data.model.Tournament
import com.example.helloandroidagain.domain.repository.ExportRepository
import javax.inject.Inject

class FakeExportRepositoryImpl @Inject constructor() : ExportRepository {
    override fun saveExportImage(tournament: Tournament, bitmap: Bitmap): Boolean = true
}
