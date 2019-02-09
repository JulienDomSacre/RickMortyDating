package com.juliensacre.rickmortydating.data.entity

import androidx.room.ColumnInfo

data class Location(
    @ColumnInfo(name = "location_name") var name: String = "",
    @ColumnInfo(name = "location_url")var url: String = ""
)