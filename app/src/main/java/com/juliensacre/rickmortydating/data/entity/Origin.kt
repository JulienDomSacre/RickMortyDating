package com.juliensacre.rickmortydating.data.entity

import androidx.room.ColumnInfo

data class Origin(
    @ColumnInfo(name = "origin_name")
    var name: String = "",
    @ColumnInfo(name = "origin_url") var url: String = ""
)