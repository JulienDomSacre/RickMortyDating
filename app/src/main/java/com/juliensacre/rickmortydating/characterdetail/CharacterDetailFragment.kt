package com.juliensacre.rickmortydating.characterdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.juliensacre.rickmortydating.R
import com.juliensacre.rickmortydating.data.Character
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_character_detail.*

class CharacterDetailFragment : Fragment() {
    companion object {
        private const val CHARACTER_ID = "characterId"
        fun newInstance(characterId : Int) = CharacterDetailFragment().apply {
            arguments = bundleOf(
                CHARACTER_ID to characterId)
        }
    }

    private lateinit var viewModel : CharacterDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_character_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(CharacterDetailViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        val stringId = arguments!!.getInt(CHARACTER_ID).toString()

        viewModel.getCharacter(stringId.toInt()).observe(this, Observer { bindData(it) })

        activity to AppCompatActivity() .supportActionBar
    }

    private fun bindData(character : Character){
        with(character){
            Picasso.with(this@CharacterDetailFragment.context).load(image).into(photo)
            status_text.text = status
            origin_text.text = origin.name
            species_text.text = species
            gender_text.text = gender
            seen_text.text = location.name
        }


    }
}