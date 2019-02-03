package com.juliensacre.rickmortydating.characters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.juliensacre.rickmortydating.R
import com.juliensacre.rickmortydating.data.CharacterLite
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_character.view.*

class CharactersViewHolder (view : View): RecyclerView.ViewHolder(view){
    companion object {
        fun create(parent: ViewGroup): CharactersViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_character, parent, false)
            return CharactersViewHolder(view)
        }
    }

    // used as an exemple: Kotlin for Android Developers
    @SuppressLint("SetTextI18n")
    fun bindTo(character: CharacterLite?) {
        with (itemView){
            Picasso.with(context).load(character?.image).into(itemView.imageView)
            text_name.text = character?.name
            text_detail.text = "${character?.species}, ${character?.origin?.name}"
        }
    }
}