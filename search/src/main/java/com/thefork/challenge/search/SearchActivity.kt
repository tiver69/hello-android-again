package com.thefork.challenge.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.thefork.challenge.api.Api
import com.thefork.challenge.api.PokemonPreview
import com.thefork.challenge.common.navigation.PokemonNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : AppCompatActivity(), SearchContract.View {

    @Inject
    lateinit var pokemonNavigator: PokemonNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val presenter = SearchPresenter(this, Api().pokemonService, lifecycleScope)
        presenter.getPokemonList(151u)
    }

    override fun displayPokemonList(pokemonList: List<PokemonPreview>) {
        findViewById<RecyclerView>(R.id.recycler_view).adapter =
            PokemonListAdapter(pokemonList) { pokemonId -> navigateToPokemon(pokemonId) }
    }

    override fun displayError() {
        Snackbar
            .make(findViewById(R.id.recycler_view), R.string.error, Snackbar.LENGTH_LONG)
            .show()
    }

    private fun navigateToPokemon(pokemonId: String) {
        pokemonNavigator.navigateToPokemonDetail(this, pokemonId)
    }
}
