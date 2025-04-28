package com.example.helloandroidagain.tournament.domain.usecase

import com.example.helloandroidagain.tournament.domain.model.Tournament
import com.example.helloandroidagain.tournament.domain.repository.TournamentRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CreateTournamentsUseCaseTest {

    @RelaxedMockK
    lateinit var repository: TournamentRepository

    @InjectMockKs
    lateinit var underTest: CreateTournamentsUseCase

    @Before
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun useCaseShouldInvokeRepository() = runTest {
        val tournament = mockk<Tournament> {}
        underTest.invoke(tournament)

        coVerify { repository.addTournament(tournament) }
    }
}
