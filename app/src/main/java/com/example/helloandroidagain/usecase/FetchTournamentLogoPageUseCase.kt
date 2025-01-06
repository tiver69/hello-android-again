package com.example.helloandroidagain.usecase

import com.example.helloandroidagain.model.TournamentLogo
import com.example.helloandroidagain.service.ImageRemoteApi
import com.example.helloandroidagain.service.ImageRetrofitInstance
import io.reactivex.rxjava3.core.Single

class FetchTournamentLogoPageUseCase {
    fun invoke(pageNumber: Int): Single<List<TournamentLogo>> =
        ImageRetrofitInstance.retrofit.create(ImageRemoteApi::class.java).searchLogo(pageNumber)
}
