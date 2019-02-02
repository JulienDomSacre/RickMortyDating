package com.juliensacre.rickmortydating.characterdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.juliensacre.rickmortydating.data.Character
import com.juliensacre.rickmortydating.data.source.remote.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class CharacterDetailViewModel @Inject constructor() : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val character = MutableLiveData<Character>()

    fun getCharacter(id : Int) : LiveData<Character> {
        //Timber.d("getCharacters call for nextPage $nextPage")
        val charactersDisposable = ApiClient.getCharacterService().getCharacter(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    character.value = it
                },
                {
                    //todo error
                }
            )

        compositeDisposable.add(charactersDisposable)
        return character
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        Timber.d("!!! cleared !!! ")
    }
}