package com.thefork.challenge.search

import com.thefork.challenge.api.PokemonPreview

class SearchContract {
    interface View {
        fun displayPokemonList(pokemonList: List<PokemonPreview>)
        fun displayError()
    }

    interface Presenter {
        fun getPokemonList(limit: UInt)
    }
}