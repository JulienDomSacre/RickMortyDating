package com.juliensacre.rickmortydating.data.source.local

import androidx.lifecycle.LiveData
import com.juliensacre.rickmortydating.data.Character
import com.juliensacre.rickmortydating.data.CharactersFull
import com.juliensacre.rickmortydating.data.Location
import com.juliensacre.rickmortydating.data.source.CharactersDataSource

class CharactersLocalDataSource : CharactersDataSource {
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