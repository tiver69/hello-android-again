package com.thefork.challenge.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: UInt = 20u,
        @Query("offset") offset: UInt = 0u,
    ): Response<Page<PokemonPreview>>

}
