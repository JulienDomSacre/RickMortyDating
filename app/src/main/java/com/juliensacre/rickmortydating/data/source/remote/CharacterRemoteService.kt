package com.juliensacre.rickmortydating.data.source.remote

import androidx.lifecycle.LiveData
import com.juliensacre.rickmortydating.data.Character
import com.juliensacre.rickmortydating.data.CharactersFull
import com.juliensacre.rickmortydating.data.LocationDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterRemoteService {
    @GET("character/?page={page}")
    fun getCharacters (@Path("page") page: Int) : LiveData<CharactersFull>

    @GET("character/{id}")
    fun getCharacter (@Path("id") characterId: Int) : LiveData<Character>

    @GET("location/{id}")
    fun getCharacterLocation (@Path("id") locationId: Int) : LiveData<LocationDetail>
}