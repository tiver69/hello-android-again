package com.thefork.challenge.search

import com.thefork.challenge.common.api.PokemonService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchPresenter @Inject constructor(
    private val pokemonService: PokemonService,
) : SearchContract.Presenter {

    private lateinit var view: SearchContract.View

    override fun attachView(view: SearchContract.View) {
        this.view = view
    }

    override fun getPokemonList(limit: UInt, scope: CoroutineScope) {
        scope.launch(Dispatchers.IO) {
            val response = pokemonService.getPokemonList(limit)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    view.displayPokemonList(response.body()!!.results)
                } else {
                    view.displayError()
                }
            }
        }
    }
}
