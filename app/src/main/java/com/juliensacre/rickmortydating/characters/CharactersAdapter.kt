package com.juliensacre.rickmortydating.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.juliensacre.rickmortydating.R
import com.juliensacre.rickmortydating.data.Character
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_character.view.*

class CharactersAdapter(val onClick: (Character) -> Unit) : RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {

    private var items: List<Character> = emptyList()

    fun loadItem(newItems : List<Character>){
        items = newItems
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_character,parent, false)
        return ViewHolder(itemView)

    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CharactersAdapter.ViewHolder, position: Int) {
        holder.bindCharacter(items[position])
        holder.itemView.setOnClickListener { onClick(items[position]) }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        // used as an exemple: Kotlin for Android Developers
        fun bindCharacter(character: Character) {
            with (character){
                Picasso.with(itemView.context).load(image).into(itemView.imageView)
                itemView.textView.text = name
            }
        }
    }
}