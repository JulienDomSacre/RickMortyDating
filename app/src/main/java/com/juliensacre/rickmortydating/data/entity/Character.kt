package com.juliensacre.rickmortydating.data.entity

data class Character(
    var id: Int,
    var name: String,
    var status: String,
    var species: String,
    var type: String,
    var gender: String,
    var image: String,
    var url: String,
    var created: String,
    var origin: Origin,
    var location: Location,
    var episode: List<String> = ArrayList() //TODO convert type
)

