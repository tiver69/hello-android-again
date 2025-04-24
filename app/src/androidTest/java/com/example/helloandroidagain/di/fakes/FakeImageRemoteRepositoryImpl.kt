package com.example.helloandroidagain.di.fakes

import com.example.helloandroidagain.tournament_domain.model.TournamentLogo
import com.example.helloandroidagain.tournament_domain.repository.ImageRemoteRepository

class FakeImageRemoteRepositoryImpl : ImageRemoteRepository {
    override suspend fun getImagePage(page: Int): List<TournamentLogo> = listOf()
}