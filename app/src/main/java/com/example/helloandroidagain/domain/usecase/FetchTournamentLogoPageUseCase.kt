package com.example.helloandroidagain.domain.usecase

import com.example.helloandroidagain.data.model.TournamentLogo
import com.example.helloandroidagain.data.repository.remote.ImageRemoteApi
import com.example.helloandroidagain.data.repository.remote.ImageRetrofitInstance
import io.reactivex.rxjava3.core.Single

class FetchTournamentLogoPageUseCase {
    fun invoke(pageNumber: Int): Single<List<TournamentLogo>> =
        ImageRetrofitInstance.retrofit.create(ImageRemoteApi::class.java).searchLogo(pageNumber)
}
