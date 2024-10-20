package com.cursokotlin.pokeapi.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.cursokotlin.pokeapi.model.LoginModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class StoreUser(private val context: Context) {
    companion object {

        const val USER_DATASTORE = "User_Datastore"
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(USER_DATASTORE)

        //Claves para las preferencias
        val USERNAME = stringPreferencesKey("username")
        val EMAIL = stringPreferencesKey("email")
        val PASSWORD = stringPreferencesKey("password")
    }

    //Flow para obtener el usuario
    val getFromLogin: Flow<LoginModel> = context.dataStore.data
        .map { preferences ->
            LoginModel(
                username = preferences[USERNAME] ?: "",
                email = preferences[EMAIL] ?: "",
                password = preferences[PASSWORD] ?: "",
            )
        }

    //Función para guardar el usuario
    suspend fun saveFromLogin(loginModel: LoginModel) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME] = loginModel.username
            preferences[EMAIL] = loginModel.email
            preferences[PASSWORD] = loginModel.password
        }
    }

    //Función para limpiar los datos del usuario
    suspend fun cleanLogin(){
      context.dataStore.edit { preferences ->
          preferences[USERNAME] = ""
          preferences[EMAIL] = ""
          preferences[PASSWORD] = ""
      }
    }
}