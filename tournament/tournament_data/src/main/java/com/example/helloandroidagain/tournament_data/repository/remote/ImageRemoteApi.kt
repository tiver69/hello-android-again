package com.example.helloandroidagain.tournament_data.repository.remote

import com.example.helloandroidagain.tournament_domain.model.TournamentLogo
import retrofit2.http.GET
import retrofit2.http.Query

const val TOURNAMENT_LOGO_PER_PAGE = 5

interface ImageRemoteApi {
    @GET("/search/photos?query=tennis&per_page=$TOURNAMENT_LOGO_PER_PAGE&orientation=landscape")
    suspend fun searchLogoSuspend(@Query("page") page: Int): List<TournamentLogo>
}
