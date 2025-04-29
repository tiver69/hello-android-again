package com.thefork.challenge.search

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.thefork.challenge.api.Api
import com.thefork.challenge.api.PokemonPreview

class SearchActivity : AppCompatActivity(), SearchContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val presenter = SearchPresenter(this, Api().pokemonService, lifecycleScope)
        presenter.getPokemonList(151u)
    }

    override fun displayPokemonList(pokemonList: List<PokemonPreview>) {
        findViewById<RecyclerView>(R.id.recycler_view).adapter = PokemonListAdapter(pokemonList)
    }

    override fun displayError() {
        Snackbar
            .make(findViewById(R.id.recycler_view), R.string.error, Snackbar.LENGTH_LONG)
            .show()
    }

    fun navigateToPokemon(pokemonPreview: PokemonPreview) {
        startActivity(Intent().apply {
            setClassName("com.thefork.challenge", "com.thefork.challenge.pokemon.PokemonActivity")
            putExtra("POKEMON_ID", pokemonPreview.id)
        })
    }
}
