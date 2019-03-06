package com.juliensacre.rickmortydating.data.remote

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.juliensacre.rickmortydating.BuildConfig
import com.juliensacre.rickmortydating.data.entity.Character
import com.juliensacre.rickmortydating.data.entity.CharactersList
import com.juliensacre.rickmortydating.data.entity.Episode
import com.juliensacre.rickmortydating.data.entity.Location
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface RickAndMortyApiService {
    @GET("character/")
    fun getAllCharacters(@Query("page") pageNumber :Int) : Deferred<CharactersList>

    @GET("character/{id}")
    fun getCharacter(@Path("id") id :Int) : Deferred<Character>

    @GET("location/{id}")
    fun getLocation (@Path("id") id :Int) : Deferred<Location>

    @GET("episode/{ids}")
    fun getEpisodes(@Path("ids") ids: List<Int>) : Deferred<List<Episode>> //TODO want test if is working

    //Singleton
    companion object {
        //operator fun invoke() <- allows you to initialize the class just with: RickAndMortyApiService()
        operator fun invoke() : RickAndMortyApiService{
            val okHttpClientBuilder = OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)

            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                okHttpClientBuilder.addInterceptor(logging)
            }

            return Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create()) // use GSOn
                .addCallAdapterFactory(CoroutineCallAdapterFactory()) //use coroutine
                .client(okHttpClientBuilder.build()) //add timeout
                .build()
                .create(RickAndMortyApiService::class.java)
        }
    }
}