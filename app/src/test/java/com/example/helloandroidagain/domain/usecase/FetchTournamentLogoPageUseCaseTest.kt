package com.example.helloandroidagain.domain.usecase

import com.example.helloandroidagain.data.model.TournamentLogo
import com.example.helloandroidagain.data.repository.remote.ImageRemoteApi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FetchTournamentLogoPageUseCaseTest {

    companion object {
        private const val LOGO_PAGE = 1
    }

    @RelaxedMockK
    lateinit var imageRemoteApi: ImageRemoteApi

    @InjectMockKs
    lateinit var underTest: FetchTournamentLogoPageUseCase

    @Before
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun useCaseShouldInvokeImageApi() = runTest {
        val tournamentLogo = mockk<TournamentLogo> {}
        val tournamentLogoList = listOf(tournamentLogo)
        coEvery { imageRemoteApi.searchLogoSuspend(LOGO_PAGE) } returns tournamentLogoList

        val result = underTest.invoke(LOGO_PAGE)
        assertEquals(tournamentLogoList, result)
    }
}