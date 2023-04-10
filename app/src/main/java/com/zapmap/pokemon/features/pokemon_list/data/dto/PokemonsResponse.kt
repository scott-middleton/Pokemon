package com.zapmap.pokemon.features.pokemon_list.data.dto

import com.squareup.moshi.Json

data class PokemonsResponse(
    @Json(name = "results")
    val pokemonItems: List<RemotePokemonItem>
)
