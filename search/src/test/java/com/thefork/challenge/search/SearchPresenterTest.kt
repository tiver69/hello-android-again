package com.thefork.challenge.search

import com.thefork.challenge.common.api.PokemonService
import com.thefork.challenge.common.api.model.Page
import com.thefork.challenge.common.api.model.PokemonPreview
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.internal.EMPTY_RESPONSE
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class SearchPresenterTest {

    companion object {
        private const val REQUEST_LIMIT = 10u
        private val RESPONSE_ENTITY = PokemonPreview("url", "PokeName")
    }

    private val dispatcher = UnconfinedTestDispatcher()

    @RelaxedMockK
    lateinit var activityMock: SearchContract.View

    @RelaxedMockK
    lateinit var serviceMock: PokemonService

    private lateinit var underTest: SearchPresenter

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)

        underTest =
            SearchPresenter(serviceMock)
        underTest.attachView(activityMock)
    }

    @Test
    fun `invokes view show method on success`() = runTest {
        val pokemonList = listOf(RESPONSE_ENTITY)
        val response = Response.success(Page(pokemonList, REQUEST_LIMIT, "", ""))

        coEvery { serviceMock.getPokemonList(REQUEST_LIMIT) } returns response

        underTest.getPokemonList(REQUEST_LIMIT, this)

        activityMock.displayPokemonList(pokemonList)
    }

    @Test
    fun `invokes displaying error method on api call failure`() = runTest {
        val errorResponse = EMPTY_RESPONSE
        coEvery { serviceMock.getPokemonList(REQUEST_LIMIT) } returns
                Response.error(404, errorResponse)

        underTest.getPokemonList(REQUEST_LIMIT, this)

        activityMock.displayError()
    }
}
