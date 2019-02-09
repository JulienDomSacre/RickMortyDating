package com.juliensacre.rickmortydating.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.juliensacre.rickmortydating.data.entity.Character

@Database(
    entities = [ Character::class],
    version = 1
)
abstract class CharacterDatabase :RoomDatabase(){
    abstract fun characterDao() : CharacterDao

    //TODO add the database build
}