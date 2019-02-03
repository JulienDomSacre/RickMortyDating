package com.juliensacre.rickmortydating.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.juliensacre.rickmortydating.data.CharacterLite
import com.juliensacre.rickmortydating.data.source.CharactersDataSource
import com.juliensacre.rickmortydating.data.source.CharactersDataSourceFactory
import com.juliensacre.rickmortydating.data.source.remote.ApiClient
import com.juliensacre.rickmortydating.util.NetworkState
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

/**
 * With help of Ahmed Abd-Elmeged (https://github.com/Ahmed-Abdelmeged/PagingLibraryWithRxJava)
 */
class CharactersViewModel @Inject constructor(): ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    var charactersList : LiveData<PagedList<CharacterLite>>
    private val pageSize = 20

    private val sourceFactory: CharactersDataSourceFactory

    init {
        sourceFactory = CharactersDataSourceFactory(compositeDisposable, ApiClient.getCharacterService())
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        charactersList = LivePagedListBuilder<Long, CharacterLite>(sourceFactory, config).build()
        Timber.d("!!! init !!! ")
    }

    fun retry() {
        sourceFactory.usersDataSourceLiveData.value!!.retry()
    }

    fun refresh() {
        sourceFactory.usersDataSourceLiveData.value!!.invalidate()
    }

    fun getNetworkState(): LiveData<NetworkState> = Transformations.switchMap<CharactersDataSource, NetworkState>(
        sourceFactory.usersDataSourceLiveData) { it.networkState }

    fun getRefreshState(): LiveData<NetworkState> = Transformations.switchMap<CharactersDataSource, NetworkState>(
        sourceFactory.usersDataSourceLiveData) { it.initialLoad }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        Timber.d("!!! cleared !!! ")
    }
}