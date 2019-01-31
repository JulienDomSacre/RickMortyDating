package com.juliensacre.rickmortydating.data.source.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    const val API_ENDPOINT = "https://rickandmortyapi.com/api/"
    fun getClient() = Retrofit.Builder()
        .baseUrl(API_ENDPOINT)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}