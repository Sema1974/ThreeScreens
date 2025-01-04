package com.pdmjosemavidal.vidalthreescreensok.model

sealed class Routes(val route: String) {
    object Screen1 : Routes("Screen1")
    object Screen2 : Routes("Screen2?username={username}") {
        fun createRoute(username: String) = "Screen2?username=$username"
    }
    object Screen3 : Routes("Screen3?sliderPosition={sliderPosition}&attempts={attempts}") {
        fun createRoute(sliderPosition: Float, attempts: String) =
            "Screen3?sliderPosition=$sliderPosition&attempts=$attempts"
    }
}