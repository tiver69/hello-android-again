package com.example.helloandroidagain.domain.usecase

import com.example.helloandroidagain.data.model.TournamentLogo
import com.example.helloandroidagain.data.repository.remote.ImageRemoteApi
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FetchTournamentLogoPageUseCase @Inject constructor(private val imageRemoteApi: ImageRemoteApi) {
    fun invoke(pageNumber: Int): Single<List<TournamentLogo>> =
        imageRemoteApi.searchLogo(pageNumber)
}
