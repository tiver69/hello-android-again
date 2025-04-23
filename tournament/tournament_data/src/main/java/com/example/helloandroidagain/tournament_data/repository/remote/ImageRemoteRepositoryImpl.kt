package com.example.helloandroidagain.tournament_data.repository.remote

import com.example.helloandroidagain.tournament_domain.model.TournamentLogo
import com.example.helloandroidagain.tournament_domain.repository.ImageRemoteRepository
import javax.inject.Inject

class ImageRemoteRepositoryImpl @Inject constructor(
    private val imageRemoteApi: ImageRemoteApi
) : ImageRemoteRepository {
    override suspend fun getImagePage(page: Int): List<TournamentLogo> =
        imageRemoteApi.searchLogoSuspend(page)
}