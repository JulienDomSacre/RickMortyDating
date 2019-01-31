package com.juliensacre.rickmortydating.characters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.juliensacre.rickmortydating.R

class CharactersFragment : Fragment() {

    companion object {
        fun newInstance() = CharactersFragment()
    }
    private lateinit var viewModel : CharactersViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_characters_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CharactersViewModel::class.java)
        viewModel.charactersList.observe(this, Observer { list -> Log.d("toto","${list.get(0).name}") })

        viewModel.getCharacters()
        // TODO: Use the ViewModel
    }
}