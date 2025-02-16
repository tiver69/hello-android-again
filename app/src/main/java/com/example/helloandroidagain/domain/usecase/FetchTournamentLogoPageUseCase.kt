package com.example.helloandroidagain.domain.usecase

import com.example.helloandroidagain.data.model.TournamentLogo
import com.example.helloandroidagain.data.repository.remote.ImageRemoteApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchTournamentLogoPageUseCase @Inject constructor(private val imageRemoteApi: ImageRemoteApi) {
    suspend fun invoke(pageNumber: Int): List<TournamentLogo> = withContext(Dispatchers.IO) {
        imageRemoteApi.searchLogoSuspend(pageNumber)
    }
}
