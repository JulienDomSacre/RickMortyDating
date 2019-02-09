package com.juliensacre.rickmortydating.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "character") //TODO update class for entity model
data class Character(
    @PrimaryKey var id: Int,
    var name: String,
    var status: String,
    var species: String,
    var type: String,
    var gender: String,
    var image: String,
    var url: String,
    var created: String,
    @Embedded var origin: Origin,
    @Embedded var location: Location
    //var episode: List<String> = ArrayList() //TODO convert type
)

