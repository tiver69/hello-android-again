package com.thefork.challenge.pokemon.presentation.navigation

import android.content.Context
import android.content.Intent
import com.thefork.challenge.common.navigation.PokemonNavigator
import com.thefork.challenge.pokemon.presentation.PokemonActivity
import javax.inject.Inject

class PokemonNavigatorImpl @Inject constructor() : PokemonNavigator {
    companion object {
        const val NAV_EXTRA_POKEMON_ID = "POKEMON_ID"
    }

    override fun navigateToPokemonDetail(context: Context, id: String) {
        val intent = Intent(context, PokemonActivity::class.java).apply {
            putExtra(NAV_EXTRA_POKEMON_ID, id)
        }
        context.startActivity(intent)
    }
}