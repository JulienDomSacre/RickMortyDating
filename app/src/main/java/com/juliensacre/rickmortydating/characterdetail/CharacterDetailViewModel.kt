package com.juliensacre.rickmortydating.characterdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.juliensacre.rickmortydating.data.Character
import com.juliensacre.rickmortydating.data.source.remote.ApiClient
import com.juliensacre.rickmortydating.util.NetworkState
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class CharacterDetailViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val character = MutableLiveData<Character>()
    private val networkState = MutableLiveData<NetworkState>()

    fun getCharacter(id : Int) : LiveData<Character> {
        // set network value to loading.
        networkState.postValue(NetworkState.LOADING)

        //get the characters from the api with id
        val charactersDisposable = ApiClient.getCharacterService().getCharacter(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    // clear retry since last request succeeded
                    setRetry(null)
                    networkState.postValue(NetworkState.LOADED)
                    character.value = it
                },
                {
                    setRetry(Action { getCharacter(id) })
                    val error = NetworkState.error(it.message)
                    // publish the error
                    networkState.postValue(error)
                }
            )

        compositeDisposable.add(charactersDisposable)
        return character
    }

    /**
     * Return the network state after receive character data
     */
    fun getNetworkState(): LiveData<NetworkState> = Transformations.switchMap(character) { networkState }

    /**
     * Keep Completable reference for the retry event
     */
    private var retryCompletable: Completable? = null

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ }, { throwable -> Timber.e(throwable.message) }))
        }
    }

    private fun setRetry(action: Action?) {
        if (action == null) {
            this.retryCompletable = null
        } else {
            this.retryCompletable = Completable.fromAction(action)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}