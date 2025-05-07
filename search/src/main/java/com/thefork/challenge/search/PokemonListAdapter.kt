package com.thefork.challenge.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.thefork.challenge.common.api.model.PokemonPreview

class PokemonListAdapter(
    private val pokemonPreviewList: List<PokemonPreview>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val spriteImageView = view.findViewById<ImageView>(R.id.sprite_image_view)
        private val nameTextView = view.findViewById<TextView>(R.id.name_text_view)

        fun bind(pokemon: PokemonPreview, onItemClick: (String) -> Unit) {
            nameTextView.text = pokemon.capitalizedName
            spriteImageView.load(pokemon.spriteUrl)
            itemView.setOnClickListener { onItemClick(pokemon.id) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pokemonPreviewList[position], onItemClick)
    }

    override fun getItemCount(): Int = pokemonPreviewList.size

}
