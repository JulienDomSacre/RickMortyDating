package com.juliensacre.rickmortydating.data.source.remote

import com.juliensacre.rickmortydating.data.Character
import com.juliensacre.rickmortydating.data.CharactersFull
import com.juliensacre.rickmortydating.data.LocationDetail
import com.juliensacre.rickmortydating.data.source.CharactersDataSource
import io.reactivex.Flowable

class CharactersRemoteDataSource : CharactersDataSource {

    override fun getCharacters(page: Int): Flowable<CharactersFull> {
        return ApiClient.getCharacterService().getCharacters(page)
    }

    override fun getCharacter(characterId: Int): Flowable<Character> {
        return ApiClient.getCharacterService().getCharacter(characterId)
    }

    override fun getCharacterLocation(locationId: Int): Flowable<LocationDetail> {
        return ApiClient.getCharacterService().getCharacterLocation(locationId)
    }

}