package com.cursokotlin.pokeapi.views.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cursokotlin.pokeapi.components.MainTopBar
import com.cursokotlin.pokeapi.components.SpacerH
import com.cursokotlin.pokeapi.model.LoginModel
import com.cursokotlin.pokeapi.store.StoreUser
import com.cursokotlin.pokeapi.util.Constants.Companion.CUSTOM_GREEN

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserView(navController: NavController) {
    val context = LocalContext.current
    val dataStore = StoreUser(context)
    val userStore = dataStore.getFromLogin.collectAsState(LoginModel())

    Scaffold(topBar = {
        MainTopBar(title = "Usuario",
            showBackButton = true,
            onClickBackButton = { navController.popBackStack() },
            onClickAction1 = {}) { }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SpacerH()
            Icon(
                imageVector = Icons.Default.AccountCircle,
                tint = Color.Black,
                modifier = Modifier.size(200.dp),
                contentDescription = ""
            )
            SpacerH()
            Text(
                text = userStore.value.username,
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = userStore.value.email,
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold
            )
            SpacerH()
            ElevatedButton(
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color(CUSTOM_GREEN),
                    contentColor = Color.White
                ),
                onClick = { navController.navigate("LoginView") }) {
                Text("Cerrar sesión")
            }
        }
    }
}