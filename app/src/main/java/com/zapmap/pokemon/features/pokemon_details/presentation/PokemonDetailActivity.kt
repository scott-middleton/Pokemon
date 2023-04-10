package com.zapmap.pokemon.features.pokemon_details.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.zapmap.pokemon.RemotePokemon
import com.zapmap.pokemon.databinding.ActivityPokemonDetailBinding
import com.zapmap.pokemon.features.pokemon_list.data.dto.RemotePokemonItem

class PokemonDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailBinding

    private lateinit var remotePokemonItem: RemotePokemonItem

    private val moshi = Moshi.Builder().build()
    private val jsonAdapter: JsonAdapter<RemotePokemonItem> = moshi.adapter(RemotePokemonItem::class.java)

    private val typesAdapter = TypesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        intent?.getStringExtra("json")?.let {
            remotePokemonItem = jsonAdapter.fromJson(it)!!
        }

        lifecycleScope.launchWhenCreated {
            val stringId = remotePokemonItem.url.removeSuffix("/").substringAfterLast("/")
            val id = stringId.toInt()
//            val response = Api.getApi().fetchPokemonById(id)
//            if (response.isSuccessful) {
//                displayPokemon(response.body()!!)
//            }
        }
        binding.recyclerViewTypes.adapter = typesAdapter

        binding.textViewPokemonName.text = remotePokemonItem.name
    }

    private fun displayPokemon(pokemon: RemotePokemon) {
        binding.apply {
            textViewPokemonName.text = pokemon.name.capitalize()
            textViewWeight.text = pokemon.weight.toString()
            textViewHeight.text = pokemon.height.toString()
            imageViewPokemonFront.load(pokemon.sprites.frontDefault)
            typesAdapter.updateItems(pokemon.types.map { it.type.name })
        }
    }
}