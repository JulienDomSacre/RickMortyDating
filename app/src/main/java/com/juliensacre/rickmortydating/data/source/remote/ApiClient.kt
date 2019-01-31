package com.juliensacre.rickmortydating.data.source.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private const val API_ENDPOINT = "https://rickandmortyapi.com/api/"
    private val charactersService : CharacterRemoteService

    init {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS) //add a 30s timeout for call
            .connectTimeout(30, TimeUnit.SECONDS)
        //if (BuildConfig.DEBUG) {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        okHttpClientBuilder.addInterceptor(logging)
        //}

        val retrofit =  Retrofit.Builder()
            .baseUrl(API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClientBuilder.build())
            .build()

        charactersService = retrofit.create(CharacterRemoteService::class.java)
    }

    fun getCharacterService() : CharacterRemoteService = charactersService
}