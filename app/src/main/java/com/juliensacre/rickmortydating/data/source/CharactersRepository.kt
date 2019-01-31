package com.juliensacre.rickmortydating.data.source

import com.juliensacre.rickmortydating.data.Character
import com.juliensacre.rickmortydating.data.CharactersFull
import com.juliensacre.rickmortydating.data.LocationDetail
import io.reactivex.Flowable

/**
 * The repository is pretty useless for the moment but I prepare the future :)
 */
class CharactersRepository (private val charactersRemoteDataSource: CharactersDataSource) : CharactersDataSource {
    //var cachedCharacter : LinkedHashMap<Int, Character> = LinkedHashMap()
    //var cachedLocationDetail : LinkedHashMap<Int, LocationDetail> = LinkedHashMap()

    override fun getCharacters(page: Int): Flowable<CharactersFull> {
        return charactersRemoteDataSource.getCharacters(page)
    }

    override fun getCharacter(characterId: Int): Flowable<Character> {
        return charactersRemoteDataSource.getCharacter(characterId)
    }

    override fun getCharacterLocation(locationId: Int): Flowable<LocationDetail> {
        return charactersRemoteDataSource.getCharacterLocation(locationId)
    }

}