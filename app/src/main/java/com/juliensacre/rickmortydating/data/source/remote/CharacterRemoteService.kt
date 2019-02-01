package com.juliensacre.rickmortydating.data.source.remote

import com.juliensacre.rickmortydating.data.Character
import com.juliensacre.rickmortydating.data.CharactersFull
import com.juliensacre.rickmortydating.data.LocationDetail
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterRemoteService {
    @GET("character/")
    fun getCharacters (@Query("page") page: Int) : Flowable<CharactersFull>

    @GET("character/{id}")
    fun getCharacter (@Path("id") characterId: Int) : Flowable<Character>

    @GET("location/{id}")
    fun getCharacterLocation (@Path("id") locationId: Int) : Flowable<LocationDetail>
}