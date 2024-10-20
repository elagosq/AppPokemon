package com.cursokotlin.pokeapi.views.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.cursokotlin.pokeapi.model.LoginModel
import com.cursokotlin.pokeapi.store.StoreUser

@Composable
fun BlankView(navController: NavController){
    val context = LocalContext.current
    val dataStore = StoreUser(context)
    val userStore = dataStore.getFromLogin.collectAsState(LoginModel())
    println(userStore)

    LaunchedEffect(Unit) {
        if(userStore.value.email == ""){
            navController.navigate("LoginView")
        }else{
            navController.navigate("HomeView")
        }
    }
}