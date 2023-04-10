package com.zapmap.pokemon.features.pokemon_list.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zapmap.pokemon.R
import com.zapmap.pokemon.features.pokemon_list.domain.model.UiPokemonItem

class PokemonAdapter(private val callback: (UiPokemonItem) -> Unit) :
    PagingDataAdapter<UiPokemonItem, PokemonAdapter.ViewHolder>(PokemonItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_pokemon, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemonItem = getItem(position)

        pokemonItem?.let {
            holder.textViewName.apply {
                text = pokemonItem.name
                setOnClickListener { callback(pokemonItem) }
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.textViewName)
    }

    class PokemonItemDiffCallback : DiffUtil.ItemCallback<UiPokemonItem>() {
        override fun areItemsTheSame(oldItem: UiPokemonItem, newItem: UiPokemonItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UiPokemonItem, newItem: UiPokemonItem): Boolean {
            return oldItem == newItem
        }
    }
}