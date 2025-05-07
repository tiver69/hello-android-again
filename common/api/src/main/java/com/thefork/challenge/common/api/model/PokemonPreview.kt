package com.thefork.challenge.common.api.model

data class PokemonPreview(
    val url: String,
    val name: String,
) {

    val id get() = url.split("/".toRegex()).dropLast(1).last()

    val spriteUrl
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"

    val capitalizedName get() = name.replaceFirstChar { it.uppercase() }
}
