package com.juliensacre.rickmortydating.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.juliensacre.rickmortydating.R
import com.juliensacre.rickmortydating.data.Character
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_character.view.*

class CharacterViewHolder (view : View): RecyclerView.ViewHolder(view){
    companion object {
        fun create(parent: ViewGroup): CharacterViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_character, parent, false)
            return CharacterViewHolder(view)
        }
    }

    // used as an exemple: Kotlin for Android Developers
    fun bindTo(character: Character?) {
        with (itemView){
            Picasso.with(context).load(character?.image).into(itemView.imageView)
            textView.text = character?.name
        }
    }
}