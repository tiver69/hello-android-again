package com.example.helloandroidagain.tournament.data.repository.remote

import com.example.helloandroidagain.tournament.domain.model.TournamentLogo
import com.example.helloandroidagain.tournament.domain.repository.ImageRemoteRepository
import javax.inject.Inject

class ImageRemoteRepositoryImpl @Inject constructor(
    private val imageRemoteApi: ImageRemoteApi
) : ImageRemoteRepository {
    override suspend fun getImagePage(page: Int): List<TournamentLogo> =
        imageRemoteApi.searchLogoSuspend(page)
}