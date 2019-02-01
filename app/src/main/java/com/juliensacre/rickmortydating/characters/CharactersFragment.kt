package com.juliensacre.rickmortydating.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.juliensacre.rickmortydating.R
import com.juliensacre.rickmortydating.util.RxBus
import com.juliensacre.rickmortydating.util.RxEvent
import kotlinx.android.synthetic.main.fragment_characters_list.*
import timber.log.Timber

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

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(CharactersViewModel::class.java)
        } ?: throw Exception("Invalid Activity")


        val adapter = CharactersAdapter{
            characterClicked(it.id) //similar to click listener
        }
        recyclerView.layoutManager = GridLayoutManager(context,2)
        recyclerView.adapter = adapter

        viewModel.getCharacters().observe(this, Observer {
            adapter.loadItem(it ?: emptyList())
            adapter.notifyDataSetChanged()
        })
    }

    private fun characterClicked(characterId : Int){
        Timber.i("click on item id: $characterId")
        RxBus.publish(RxEvent.CharacterSelected(characterId))
    }
}