package com.juliensacre.rickmortydating.data

import com.juliensacre.rickmortydating.data.local.CharacterDao
import com.juliensacre.rickmortydating.data.remote.IChararterDataSource

class CharacterRepositoryImpl (
    private val characterDao: CharacterDao,
    private val characterDataSource : IChararterDataSource)
    : ICharacterRepository{
    //TODO
}