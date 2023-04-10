package com.zapmap.pokemon.core.domain.mappers

import com.zapmap.pokemon.core.domain.model.UiPokemon
import com.zapmap.pokemon.features.pokemon_list.data.dto.RemotePokemonItem
import java.util.*

fun RemotePokemonItem.toUiPokemon(): UiPokemon {

    val id = this.url.removeSuffix("/").substringAfterLast("/").toInt()
    val name =
        this.name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }

    return UiPokemon(
        id = id,
        name = name
    )
}