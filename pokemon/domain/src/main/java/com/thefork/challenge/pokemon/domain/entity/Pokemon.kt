package com.thefork.challenge.pokemon.domain.entity

data class Pokemon(
    val id: Long,
    val name: String,
    val logoUrl: String,
    val speciesColor: SpeciesColor?,
    val height: Float,
    val weight: Int,
    val types: List<String>,
    val stats: List<Stat>,
) {
    data class Stat(
        val name: String,
        val value: Int
    )

    enum class SpeciesColor(val naming: String, val argb: Int) {
        BLACK("black", -16777216),
        BLUE("blue", -16776961),
        BROWN("brown", -5952982),
        GRAY("gray", -7829368),
        GREEN("green", -16711936),
        PINK("pink", -16181),
        PURPLE("purple", -8388480),
        RED("red", -65536),
        WHITE("white", -1),
        YELLOW("yellow", -256);

        companion object {
            fun fromNaming(name: String): SpeciesColor? {
                return entries.find { it.naming.equals(name.trim(), ignoreCase = true) }
            }
        }
    }
}
