package com.example.helloandroidagain.tournament_domain.usecase

import com.example.helloandroidagain.tournament_domain.model.TournamentLogo
import com.example.helloandroidagain.tournament_domain.repository.ImageRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchTournamentLogoPageUseCase @Inject constructor(
    private val imageRemoteRepository: ImageRemoteRepository
) {
    suspend fun invoke(pageNumber: Int): List<TournamentLogo> = withContext(Dispatchers.IO) {
        imageRemoteRepository.getImagePage(pageNumber)
    }
}
