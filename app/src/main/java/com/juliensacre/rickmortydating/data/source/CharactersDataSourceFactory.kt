package com.juliensacre.rickmortydating.data.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.juliensacre.rickmortydating.data.CharacterLite
import com.juliensacre.rickmortydating.data.source.remote.CharacterRemoteService
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Ahmed Abd-Elmeged on 2/20/2018.
 *
 * A simple data source factory which also provides a way to observe the last created data source.
 * This allows us to channel its network request status etc back to the UI. See the Listing creation
 * in the Repository class.
 */
class CharactersDataSourceFactory(private val compositeDisposable: CompositeDisposable,
                             private val characterRemoteService: CharacterRemoteService)
    : DataSource.Factory<Long, CharacterLite>() {

    val usersDataSourceLiveData = MutableLiveData<CharactersDataSource>()

    override fun create(): DataSource<Long, CharacterLite> {
        val usersDataSource = CharactersDataSource(characterRemoteService, compositeDisposable)
        usersDataSourceLiveData.postValue(usersDataSource)
        return usersDataSource
    }

}