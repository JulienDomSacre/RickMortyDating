package com.juliensacre.rickmortydating.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.juliensacre.rickmortydating.data.Character
import com.juliensacre.rickmortydating.data.source.remote.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CharactersViewModel @Inject constructor(): ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val charactersList = MutableLiveData<ArrayList<Character>>()
    private var page = 1

    init {
        charactersList.value = ArrayList()
    }


    fun getCharacters() : LiveData<ArrayList<Character>>{
        val charactersDisposable =  ApiClient.getCharacterService().getCharacters(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    charactersList.value!!.addAll(it.results)
                    charactersList.value = charactersList.value

                    page = it.info.next.removePrefix("https://rickandmortyapi.com/api/character/?page=").toInt()
                },
                {
                    //todo error
                }
            )

        compositeDisposable.add(charactersDisposable)
        return charactersList
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }
}