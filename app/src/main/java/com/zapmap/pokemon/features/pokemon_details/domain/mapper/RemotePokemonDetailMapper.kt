package com.zapmap.pokemon.features.pokemon_details.domain.mapper

import com.zapmap.pokemon.features.pokemon_details.data.dto.RemotePokemonDetail
import com.zapmap.pokemon.features.pokemon_details.domain.model.UiPokemonDetail
import java.util.*

fun RemotePokemonDetail.toUiPokemonDetail(): UiPokemonDetail {
    val name = this.name.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }

    return UiPokemonDetail(
        name = name,
        weight = this.weight,
        height = this.height,
        typeNames = this.types.map { it.type.name },
        spriteFrontDefault = this.sprites.frontDefault
    )
}