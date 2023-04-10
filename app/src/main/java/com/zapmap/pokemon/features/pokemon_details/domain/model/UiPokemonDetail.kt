package com.zapmap.pokemon.features.pokemon_details.domain.model

data class UiPokemonDetail(
    val name: String,
    val weight: Int,
    val height: Int,
    val spriteFrontDefault: String,
    val typeNames: List<String>,
)