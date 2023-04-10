package com.zapmap.pokemon.features.pokemon_details.domain.mapper

import com.zapmap.pokemon.features.pokemon_details.data.dto.RemotePokemonDetail
import com.zapmap.pokemon.features.pokemon_details.domain.model.UiPokemonDetail
import java.util.*

fun RemotePokemonDetail.toUiPokemonDetail(): UiPokemonDetail {
    val name = this.name.capitaliseFirstCharacter()

    return UiPokemonDetail(
        name = name,
        weight = this.weight,
        height = this.height,
        typeNames = this.types.map { it.type.name.capitaliseFirstCharacter() },
        spriteFrontDefault = this.sprites.frontDefault
    )
}

private fun String.capitaliseFirstCharacter(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else this
    }
}

