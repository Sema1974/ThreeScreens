package com.pdmjosemavidal.vidalthreescreensok

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pdmjosemavidal.vidalthreescreensok.ui.theme.VidalThreeScreensOKTheme
import com.pdmjosemavidal.vidalthreescreensok.model.Routes
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VidalThreeScreensOKTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Routes.Screen1.route
                    ) {
                        composable(Routes.Screen1.route) { Screen1(navController) }
                        composable(
                            Routes.Screen2.route,
                            arguments = listOf(navArgument("username") { defaultValue = "Guest" })
                        ) { backStackEntry ->
                            Screen2(
                                navController,
                                backStackEntry.arguments?.getString("username") ?: "Guest"
                            )
                        }
                        composable(
                            Routes.Screen3.route,
                            arguments = listOf(
                                navArgument("sliderPosition") { defaultValue = 1f },
                                navArgument("attempts") { defaultValue = "1" }
                            )
                        ) { backStackEntry ->
                            Screen3(
                                sliderPosition = backStackEntry.arguments?.getFloat("sliderPosition") ?: 1f,
                                attempts = backStackEntry.arguments?.getString("attempts") ?: "1"
                            )
                        }
                    }
                }
            }
        }
    }
}


