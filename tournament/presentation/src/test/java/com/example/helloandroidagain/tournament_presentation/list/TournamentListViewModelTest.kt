package com.example.helloandroidagain.tournament.presentation.list

import com.example.helloandroidagain.tournament.domain.model.Tournament
import com.example.helloandroidagain.tournament.domain.usecase.CreateTournamentsUseCase
import com.example.helloandroidagain.tournament.domain.usecase.LoadTournamentsUseCase
import com.example.helloandroidagain.tournament.domain.usecase.RemoveTournamentsUseCase
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TournamentListViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @MockK
    lateinit var loadTournamentsUseCaseMock: LoadTournamentsUseCase

    @RelaxedMockK
    lateinit var createTournamentsUseCaseMock: CreateTournamentsUseCase

    @RelaxedMockK
    lateinit var removeTournamentsUseCaseMock: RemoveTournamentsUseCase

    @InjectMockKs
    lateinit var underTest: TournamentListViewModel

    @Before
    fun init() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `restoreTournaments should call loadTournamentsUseCase and emit tournaments`() = runTest {
        val tournaments = listOf(
            mockk<Tournament> {},
            mockk<Tournament> {}
        )
        val tournamentsFlow = flowOf(tournaments)
        every { loadTournamentsUseCaseMock.invoke() } returns tournamentsFlow

        underTest.restoreTournaments()
        advanceUntilIdle()

        val collectedTournamentsSkippingDefault = underTest.tournamentsFlow.drop(1).first()
        coVerify { loadTournamentsUseCaseMock.invoke() }
        assertEquals(tournaments, collectedTournamentsSkippingDefault)
    }

    @Test
    fun `createTournament should call createTournamentsUseCase`() = runTest {
        val tournament = mockk<Tournament> {}
        underTest.createTournament(tournament)
        advanceUntilIdle()

        coVerify { createTournamentsUseCaseMock.invoke(tournament) }
    }

    @Test
    fun `removeTournament should call removeTournamentsUseCase with correct ID`() = runTest {
        underTest.tournamentsFlow = mockk<StateFlow<List<Tournament>>> {
            every { value } returns listOf(
                mockk<Tournament> {},
                mockk<Tournament> {
                    every { id } returns 17
                }
            )
        }

        underTest.removeTournament(1)
        advanceUntilIdle()

        coVerify { removeTournamentsUseCaseMock.invoke(17) }
    }
}