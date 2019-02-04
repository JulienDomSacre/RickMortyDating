package com.juliensacre.rickmortydating.data.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.juliensacre.rickmortydating.data.CharacterLite
import com.juliensacre.rickmortydating.data.source.remote.CharacterRemoteService
import com.juliensacre.rickmortydating.util.NetworkState
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * With help of Ahmed Abd-Elmeged (https://github.com/Ahmed-Abdelmeged/PagingLibraryWithRxJava)
 */
class CharactersDataSource (
    private val characterService : CharacterRemoteService,
    private val compositeDisposable : CompositeDisposable)
    :ItemKeyedDataSource<Long, CharacterLite>(){

    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    private var pageMax = 0
    private var nextPage = 0
    private var isEndList = false

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

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<CharacterLite>) {
        if(!isEndList) {
            // update network states.
            // we also provide an initial load state to the listeners so that the UI can know when the
            // very first list is loaded.
            networkState.postValue(NetworkState.LOADING)
            initialLoad.postValue(NetworkState.LOADING)

            //get the initial characters from the api
            compositeDisposable.add(characterService.getCharacters(1).subscribe({
                // clear retry since last request succeeded
                setRetry(null)
                networkState.postValue(NetworkState.LOADED)
                initialLoad.postValue(NetworkState.LOADED)
                pageMax = it.info.pages
                callback.onResult(it.results)
                checkIsAllLoad(it.info.next)
                Timber.d("get character page 1")
            }, {
                // keep a Completable for future retry
                setRetry(Action { loadInitial(params, callback) })
                val error = NetworkState.error(it.message)
                // publish the error
                networkState.postValue(error)
                initialLoad.postValue(error)
            }))
        }
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<CharacterLite>) {
        if(!isEndList) {
            // set network value to loading.
            networkState.postValue(NetworkState.LOADING)

            //get the characters from the api
            compositeDisposable.add(characterService.getCharacters(params.key.toInt()).subscribe({
                setRetry(null)
                networkState.postValue(NetworkState.LOADED)
                callback.onResult(it.results)
                checkIsAllLoad(it.info.next)
                Timber.d("get character page ${params.key}")
            }, {
                // keep a Completable for future retry
                setRetry(Action { loadAfter(params, callback) })
                // publish the error
                networkState.postValue(NetworkState.error(it.message))
            }))
        }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<CharacterLite>) {
        //nothing
    }

    override fun getKey(item: CharacterLite): Long {
        return nextPage.toLong()
    }

    private fun setRetry(action: Action?) {
        if (action == null) {
            this.retryCompletable = null
        } else {
            this.retryCompletable = Completable.fromAction(action)
        }
    }

    private fun checkIsAllLoad(nextPageReceived: String){
        if(nextPage == pageMax) isEndList=true
        if(!nextPageReceived.isEmpty())
            nextPage = nextPageReceived.substring(nextPageReceived.lastIndexOf("=") + 1).toInt()
    }
}