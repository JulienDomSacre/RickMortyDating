package com.juliensacre.rickmortydating.data

import android.location.Location

data class CharactersFull(
    val info: Info,
    val results: List<Character>
)

data class Info(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: String
)

data class Character(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)

data class CharacterLite(
    val id: Int,
    val image: String,
    val name: String,
    val url: String
)

data class Location(
    val name: String,
    val url: String
)

data class Origin(
    val name: String,
    val url: String
)

data class LocationDetail(
    val dimension: String,
    val id: Int,
    val name: String,
    val type: String
)