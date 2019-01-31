package com.juliensacre.rickmortydating.data.source

import androidx.lifecycle.LiveData
import com.juliensacre.rickmortydating.data.Character
import com.juliensacre.rickmortydating.data.CharactersFull
import com.juliensacre.rickmortydating.data.Location

class CharactersRepository(val charactersLocalDataSource: CharactersDataSource,
                           val charactersRemoteDataSource: CharactersDataSource) : CharactersDataSource {
    override fun getCharacters(page: Int): LiveData<CharactersFull> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCharacter(characterId: String): LiveData<Character> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCharacterLocation(locationId: String): LiveData<Location> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}