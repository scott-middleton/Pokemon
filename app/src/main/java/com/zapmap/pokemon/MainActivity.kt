package com.zapmap.pokemon

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.zapmap.pokemon.databinding.ActivityMainBinding
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    companion object {
        private const val LIMIT = 50
    }

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val pokemonAdapter = PokemonAdapter { pokemonItem -> onPokemonClicked(pokemonItem) }

    private val moshi = Moshi.Builder().build()
    private val jsonAdapter: JsonAdapter<RemotePokemonItem> = moshi.adapter(RemotePokemonItem::class.java)

    private var currentOffset = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerViewPokemon.adapter = pokemonAdapter
        binding.recyclerViewPokemon.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    lifecycleScope.launch {
                        fetchPokemon()
                    }
                }
            }
        })

        lifecycleScope.launchWhenCreated {
            fetchPokemon()
        }
    }

    private suspend fun fetchPokemon() {
        val response = Api.getApi().fetchPokemons(limit = LIMIT, offset = currentOffset)
        currentOffset += LIMIT
        if (response.isSuccessful) {
            val list = response.body()?.pokemonItems.orEmpty()
            pokemonAdapter.updateItems(list)
        }
    }

    private fun onPokemonClicked(pokemonItem: RemotePokemonItem) {
        val stringId = pokemonItem.url.removeSuffix("/").substringAfterLast("/")
        val id = stringId.toInt()
        ZoogleAnalytics.logEvent(ZoogleAnalyticsEvent("pokemon_selected", mapOf("id" to id.toString())))

        val json = jsonAdapter.toJson(pokemonItem)
        Intent(this@MainActivity, PokemonDetailActivity::class.java).apply {
            putExtra("json", json)
            startActivity(this@apply)
        }
    }
}