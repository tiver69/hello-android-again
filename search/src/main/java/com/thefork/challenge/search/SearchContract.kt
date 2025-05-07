package com.thefork.challenge.search

import com.thefork.challenge.common.api.model.PokemonPreview
import kotlinx.coroutines.CoroutineScope

class SearchContract {
    interface View {
        fun displayPokemonList(pokemonList: List<PokemonPreview>)
        fun displayError()
    }

    interface Presenter {
        fun attachView(view: View)
        fun getPokemonList(limit: UInt, scope: CoroutineScope)
    }
}