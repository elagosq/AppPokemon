package com.cursokotlin.pokeapi.components

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.cursokotlin.pokeapi.model.PokemonList
import com.cursokotlin.pokeapi.util.Constants.Companion.CUSTOM_GREEN


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    title: String,
    showBackButton: Boolean = false,
    onClickBackButton: () -> Unit,
    onClickAction1: () -> Unit,
    onClickAction2: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = title, color = Color.Black, fontWeight = FontWeight.ExtraBold) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.White
        ),
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = { onClickBackButton() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "",
                        tint = Color.Black
                    )
                }
            }
        },
        actions = {
            if (!showBackButton) {
                IconButton(onClick = { onClickAction1() }) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "",
                        tint = Color.Black
                    )
                }
                IconButton(onClick = { onClickAction2() }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "",
                        tint = Color.Black
                    )
                }
            }
        }
    )
}

@Composable
fun ItemPoke(item: PokemonList, function: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { function() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val components = item.url.split("/")
            val id = components[components.size - 2].toIntOrNull() ?: 0
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                MainImage(
                    image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png",
                    w = 80,
                    h = 80
                )
                Text(text = item.name, color = Color.Black, fontWeight = FontWeight.ExtraBold)
                IconToggleFavorite()
            }
        }
    }
}

@Composable
fun MainImage(image: String, w: Int, h: Int) {
    val image = rememberAsyncImagePainter(model = image)
    Image(
        painter = image,
        contentDescription = null,
        modifier = Modifier
            .width(w.dp)
            .height(h.dp)
    )
}

@Composable
fun Alert(
    title: String,
    message: String,
    confirmText: String,
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismissClick() },
        title = { Text(text = title) },
        text = {
            Text(text = message, textAlign = TextAlign.Justify)
        },
        confirmButton = {
            Button(onClick = { onConfirmClick() }) {
                Text(text = confirmText)
            }
        }
    )
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun IconToggleFavorite() {
    val checkState = remember { mutableStateOf(false) }
    IconToggleButton(
        checked = checkState.value,
        onCheckedChange = {
            checkState.value = !checkState.value
        }) {
        val transition = updateTransition(targetState = checkState.value, label = "")
        val myTint by transition.animateColor(label = "") { isChecked ->
            if (isChecked) {
                Color.Red
            } else {
                Color.Black
            }
        }
        val mySize by transition.animateDp(
            label = "",
            transitionSpec = {
                keyframes {
                    durationMillis = 250
                    120.dp at 0 with LinearOutSlowInEasing
                    125.dp at 30 with FastOutLinearInEasing
                }
            }
        )
        { 100.dp }
        Icon(
            imageVector = if (checkState.value) {
                Icons.Filled.Favorite
            } else {
                Icons.Filled.FavoriteBorder
            },
            contentDescription = "",
            modifier = Modifier.size(mySize),
            tint = myTint
        )
    }
}

@Composable
fun Loading() {
    CircularProgressIndicator(
        modifier = Modifier.width(64.dp),
        color = Color(CUSTOM_GREEN),
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}

@Composable
fun ButtonGoBack() {
    var showConfirmDialog by remember { mutableStateOf(false) }
    BackHandler(true) {
        showConfirmDialog = true
    }
    if (showConfirmDialog) {
        ConfirmDialog(
            onDismissRequest = { showConfirmDialog = false }
        )
    }
}

@Composable
fun ConfirmDialog(
    onDismissRequest: () -> Unit
) {
    val activity = LocalContext.current as Activity
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                6.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .height(150.dp)
                .background(Color.White, RoundedCornerShape(8.dp))
        ) {
            Text(text = "Estás por salir de la aplicación", fontSize = 14.sp)
            Text(text = "¿Deseas continuar?", fontSize = 14.sp)
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = { onDismissRequest() },
                ) {
                    Text(text = "Cancelar", fontSize = 14.sp)
                }
                TextButton(
                    onClick = { activity?.finish() },
                ) {
                    Text(text = "Ok", fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun SpacerH(){
    Spacer(Modifier.height(15.dp))
}
