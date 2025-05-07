package com.thefork.challenge.common.navigation

import android.content.Context

interface PokemonNavigator {
    fun navigateToPokemonDetail(context: Context, id: String)
}