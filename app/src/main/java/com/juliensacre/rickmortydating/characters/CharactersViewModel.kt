package com.juliensacre.rickmortydating.characters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.juliensacre.rickmortydating.data.Character
import com.juliensacre.rickmortydating.data.source.remote.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CharactersViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val charactersList = MutableLiveData<List<Character>>()

    fun getCharacters(){
        val charactersDisposable =  ApiClient.getCharacterService().getCharacters()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->  charactersList.value = it.results}

        compositeDisposable.add(charactersDisposable)
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }
}