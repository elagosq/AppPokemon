package com.cursokotlin.pokeapi.di

import com.cursokotlin.pokeapi.data.ApiPokemon
import com.cursokotlin.pokeapi.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//Configuración de la inyección de dependencia
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    //Configuración de retrofit
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesAPIGames(retrofit: Retrofit) : ApiPokemon {
        return retrofit.create(ApiPokemon::class.java)
    }
}