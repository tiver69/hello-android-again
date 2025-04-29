package com.thefork.challenge.search

import com.thefork.challenge.api.PokemonService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchPresenter(
    private val view: SearchContract.View,
    private val pokemonService: PokemonService,
    private val scope: CoroutineScope,
) : SearchContract.Presenter {

    override fun getPokemonList(limit: UInt) {
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
