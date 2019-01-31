package com.juliensacre.rickmortydating.data.source

import androidx.lifecycle.LiveData
import com.juliensacre.rickmortydating.data.Character
import com.juliensacre.rickmortydating.data.CharactersFull
import com.juliensacre.rickmortydating.data.Location

interface CharactersDataSource {
    fun getCharacters(page: Int) : LiveData<CharactersFull>
    fun getCharacter(characterId : String) : LiveData<Character>
    fun getCharacterLocation(locationId : String) : LiveData<Location>
}