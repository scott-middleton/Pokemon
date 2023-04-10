package com.zapmap.pokemon.features.pokemon_list.domain

import com.zapmap.pokemon.features.pokemon_list.domain.mappers.model.UiPokemonItem
import com.zapmap.pokemon.features.pokemon_list.data.dto.RemotePokemonItem
import java.util.*

fun RemotePokemonItem.toUiPokemon(): UiPokemonItem {

    val id = this.url.removeSuffix("/").substringAfterLast("/").toInt()
    val name =
        this.name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }

    return UiPokemonItem(
        id = id,
        name = name
    )
}