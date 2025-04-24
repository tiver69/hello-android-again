package com.example.helloandroidagain.presentation.tournament.create

import com.example.helloandroidagain.tournament_domain.model.TournamentLogo
import com.example.helloandroidagain.tournament_domain.usecase.FetchTournamentLogoPageUseCase
import com.example.helloandroidagain.tournament_presentation.create.TournamentCreateViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TournamentCreateViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @MockK
    lateinit var fetchTournamentLogoPageUseCase: FetchTournamentLogoPageUseCase

    @RelaxedMockK
    lateinit var analytics: FirebaseAnalytics

    @InjectMockKs
    lateinit var underTest: TournamentCreateViewModel

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
    fun viewModelShouldFetchTwoImagesOfFistPage() = runTest {
        val logos = listOf(mockk<TournamentLogo> {}, mockk<TournamentLogo> {})
        coEvery { fetchTournamentLogoPageUseCase.invoke(1) } returns logos

        val resultLogos = mutableListOf<TournamentLogo?>()
        val job = launch {
            underTest.currentLogo.toList(resultLogos)
        }

        underTest.fetchTournamentLogoPage()
        advanceUntilIdle()
        underTest.regenerateTournamentLogo()
        advanceUntilIdle()

        assertEquals(3, resultLogos.size)
        assertEquals(logos[0], resultLogos[1])
        assertEquals(logos[1], resultLogos[2])
        job.cancel()
    }

    @Test
    fun viewModelShouldSwitchToFetchImagesOfSecondPage() = runTest {
        val logosFirstPage = listOf(
            mockk<TournamentLogo> {},
            mockk<TournamentLogo> {},
            mockk<TournamentLogo> {},
            mockk<TournamentLogo> {},
            mockk<TournamentLogo> {}
        )
        val logosSecondPage = listOf(mockk<TournamentLogo> {})
        coEvery { fetchTournamentLogoPageUseCase.invoke(1) } returns logosFirstPage
        coEvery { fetchTournamentLogoPageUseCase.invoke(2) } returns logosSecondPage

        val resultLogos = mutableListOf<TournamentLogo?>()
        val job = launch {
            underTest.currentLogo.toList(resultLogos)
        }

        for (i in 0..4) {
            underTest.regenerateTournamentLogo()
            advanceUntilIdle()
        }

        underTest.regenerateTournamentLogo()
        advanceUntilIdle()

        assertEquals(7, resultLogos.size)
        assertEquals(logosSecondPage[0], resultLogos[6])
        job.cancel()
    }
}