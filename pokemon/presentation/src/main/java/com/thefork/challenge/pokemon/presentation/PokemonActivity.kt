package com.thefork.challenge.pokemon.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.thefork.challenge.pokemon.presentation.navigation.PokemonNavigatorImpl.Companion.NAV_EXTRA_POKEMON_ID
import com.thefork.challenge.pokemon.presentation.theme.PokemonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonActivity : AppCompatActivity() {

    private val pokemonId: String by lazy {
        intent.getStringExtra(NAV_EXTRA_POKEMON_ID) ?: "0"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonTheme {
                PokemonScreen(pokemonId)
            }
        }
    }
}