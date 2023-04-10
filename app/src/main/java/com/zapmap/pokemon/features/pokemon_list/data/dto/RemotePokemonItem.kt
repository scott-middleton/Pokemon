package com.zapmap.pokemon.features.pokemon_list.data.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemotePokemonItem(val name: String, val url: String)