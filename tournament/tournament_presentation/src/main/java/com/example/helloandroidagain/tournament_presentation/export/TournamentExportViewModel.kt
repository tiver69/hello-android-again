package com.example.helloandroidagain.tournament_presentation.export

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.helloandroidagain.tournament_domain.model.Tournament
import com.example.helloandroidagain.tournament_domain.usecase.ExportTournamentUseCase
import com.example.helloandroidagain.tournament_presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TournamentExportViewModel @Inject constructor(
    private val exportTournamentUseCase: ExportTournamentUseCase
) : ViewModel() {

    private val _saveImageResultMessage = MutableStateFlow<Int?>(null)
    val saveImageResultMessage: StateFlow<Int?> = _saveImageResultMessage.asStateFlow()

    var isImageSaved: Boolean = false
        private set

    fun saveImageToMediaStore(tournament: Tournament, bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            isImageSaved = exportTournamentUseCase.invoke(tournament, bitmap)
            _saveImageResultMessage.value = if (isImageSaved) {
                R.string.export_tournament_success_message
            } else {
                R.string.export_tournament_fail_message
            }
        }
    }
}