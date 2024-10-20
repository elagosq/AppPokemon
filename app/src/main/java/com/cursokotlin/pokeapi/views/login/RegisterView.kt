package com.cursokotlin.pokeapi.views.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.cursokotlin.pokeapi.store.StoreUser
import com.cursokotlin.pokeapi.components.Alert
import com.cursokotlin.pokeapi.model.LoginModel
import com.cursokotlin.pokeapi.util.Constants.Companion.CUSTOM_GREEN
import kotlinx.coroutines.launch

@Composable
fun RegisterView() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = StoreUser(context)
    println(dataStore)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        var email by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        var username by rememberSaveable { mutableStateOf("") }
        var showalerta by rememberSaveable { mutableStateOf(false) }
        val userStore = dataStore.getFromLogin.collectAsState(LoginModel())

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp, top = 20.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp, top = 20.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp, top = 20.dp, bottom = 20.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))
      //
        Button(
            onClick = {
                if (email == "" && username == "" && password == "") {
                    Toast.makeText(context,"Los campos son obligatorio", Toast.LENGTH_SHORT).show()
                } else {
                    if(email == userStore.value.email && username == userStore.value.username && password == userStore.value.password){
                        Toast.makeText(context, "El usuario ya existe", Toast.LENGTH_SHORT).show()
                        email = ""
                        password = ""
                        username = ""
                    } else {
                        scope.launch {
                            dataStore.saveFromLogin(
                                LoginModel(
                                    username = username,
                                    email = email,
                                    password = password,
                                )
                            )
                            showalerta = true
                            email = ""
                            password = ""
                            username = ""
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(
                    CUSTOM_GREEN,
                ),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
        ) {
            Text(text = "Registrarse")
        }

        if (showalerta) {
            Alert(
                title = "Alerta",
                message = "Usuario creado",
                confirmText = "Aceptar",
                onConfirmClick = { showalerta = false }) {}
        }
    }
}