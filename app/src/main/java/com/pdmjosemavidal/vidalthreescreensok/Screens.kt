package com.pdmjosemavidal.vidalthreescreensok

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pdmjosemavidal.vidalthreescreensok.model.Routes
import kotlin.random.Random


@Composable
fun Screen1(navController: NavController) {
    val users = mapOf("sema" to "sema1", "bego" to "bego1", "diego" to "diego1", "hector" to "hector1")
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            placeholder = { Text (text = "Username") },

        )

        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            //label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            placeholder = { Text (text = "Password") },
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {
                if (users[username] == password) {
                    navController.navigate(Routes.Screen2.createRoute(username))
                } else {
                    showError = true
                }
            }) {
                Text("LogIn")
            }
            Button(onClick = {
                navController.navigate(Routes.Screen2.createRoute("Guest"))
            }) {
                Text("Guest")
            }
        }
        if (showError) {
            Text("Invalid Username or password", color = MaterialTheme.colorScheme.error)
        }
    }
}

@Composable
fun Screen2(navController: NavController, username: String) {
    var sliderPosition by remember { mutableStateOf(1f) }
    var attempts by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Hola, $username!", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text(":")
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = 1f..10f,
            steps = 8
        )
        Text(text = sliderPosition.toInt().toString())
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = attempts,
            onValueChange = { attempts = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            placeholder = { Text ( text = "Number of attempts") },

        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            navController.navigate(Routes.Screen3.createRoute(sliderPosition, attempts))
        }, enabled = attempts.isNotEmpty()) {
            Text("Play")
        }
    }
}

@Composable
fun Screen3(navController: NavController, sliderPosition: Float, attempts: String) {
    val randomNumber = remember { Random.nextInt(1, 11) }
    var currentAttempt by remember { mutableStateOf(1) }
    var message by remember { mutableStateOf(" You have $attempts attempts to guess the number") }
    var attemptMade by remember { mutableStateOf(false) }
    var guessedCorrectly by remember { mutableStateOf(false) }
    var currentSliderPosition by remember { mutableStateOf(sliderPosition) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Slider(
            value = currentSliderPosition,
            onValueChange = { currentSliderPosition = it },
            valueRange = 1f..10f,
            steps = 8,
            enabled = !attemptMade && !guessedCorrectly
        )
        Text(text = currentSliderPosition.toInt().toString())
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (currentSliderPosition.toInt() == randomNumber) {
                    message = "You guess the number: $randomNumber in your attempt: $currentAttempt"
                    guessedCorrectly = true
                } else {
                    currentAttempt++
                    if (currentAttempt > attempts.toInt()) {
                        message = " You didn´t guess the number $randomNumber."
                        attemptMade = true
                    } else {
                        message = "Attempt $currentAttempt: try Again!"
                    }
                }
            },
            enabled = !attemptMade && !guessedCorrectly
        ) {
            Text("Try Again")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            navController.navigate(Routes.Screen1.route)
        }
        ){
            Text("Go Back")
        }
    }
}
