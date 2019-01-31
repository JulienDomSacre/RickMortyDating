package com.juliensacre.rickmortydating.data.source

import com.juliensacre.rickmortydating.data.Character
import com.juliensacre.rickmortydating.data.CharactersFull
import com.juliensacre.rickmortydating.data.LocationDetail
import io.reactivex.Flowable

interface CharactersDataSource {
    fun getCharacters(page: Int = 1) : Flowable<CharactersFull>
    fun getCharacter(characterId : Int) : Flowable<Character>
    fun getCharacterLocation(locationId: Int): Flowable<LocationDetail>
}