package com.example.helloandroidagain.usecase

import com.example.helloandroidagain.model.TournamentLogo
import com.example.helloandroidagain.service.ImageRemoteService
import com.example.helloandroidagain.service.ImageRetrofitInstance
import io.reactivex.rxjava3.core.Single

class FetchTournamentLogoPageUseCase {
    fun execute(pageNumber: Int): Single<List<TournamentLogo>> =
        ImageRetrofitInstance.retrofit.create(ImageRemoteService::class.java).searchLogo(pageNumber)
}
