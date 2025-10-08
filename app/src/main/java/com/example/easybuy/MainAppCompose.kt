package com.example.easybuy

import FavouritesScreen
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.easybuy.feature.Auth.signin.SignInScreen
import com.example.easybuy.feature.Auth.signup.SignUpScreen
import com.example.easybuy.Splash.SplashScreen
import com.example.easybuy.feature.conclusion.ProfileScreen
import com.example.easybuy.feature.home.HomeScreen
import com.example.easybuy.feature.settings.SettingsScreen

@Composable
fun EasyBuyApp() {
    Surface(modifier = Modifier.fillMaxSize()) {
         val navController = rememberNavController()
       NavHost(navController = navController, startDestination = "splash"){

           composable(
               route = "splash",
               enterTransition = { fadeIn(animationSpec = tween(300)) },
               exitTransition = { fadeOut(animationSpec = tween(300)) }
           ) {
               SplashScreen(navController)
           }

           composable(
               route = "signin",
               enterTransition = {
                   slideInHorizontally(
                       initialOffsetX = { fullWidth -> fullWidth }, // Start from right
                       animationSpec = tween(300) // 300ms duration
                   )
               },
               exitTransition = {
                   slideOutHorizontally(
                       targetOffsetX = { fullWidth -> -fullWidth }, // Exit to left
                       animationSpec = tween(300)
                   )
               },
               popEnterTransition = {
                   slideInHorizontally(
                       initialOffsetX = { fullWidth -> -fullWidth }, // Come back from left
                       animationSpec = tween(300)
                   )
               },
               popExitTransition = {
                   slideOutHorizontally(
                       targetOffsetX = { fullWidth -> fullWidth }, // Exit to right
                       animationSpec = tween(300)
                   )
               }
           ) {
               SignInScreen(navController)
           }


           composable(
               route = "signup",
               enterTransition = {
                   slideInHorizontally(
                       initialOffsetX = { fullWidth -> fullWidth }, // Start from right
                       animationSpec = tween(300) // 300ms duration
                   )
               },
               exitTransition = {
                   slideOutHorizontally(
                       targetOffsetX = { fullWidth -> -fullWidth }, // Exit to left
                       animationSpec = tween(300)
                   )
               },
               popEnterTransition = {
                   slideInHorizontally(
                       initialOffsetX = { fullWidth -> -fullWidth }, // Come back from left
                       animationSpec = tween(300)
                   )
               },
               popExitTransition = {
                   slideOutHorizontally(
                       targetOffsetX = { fullWidth -> fullWidth }, // Exit to right
                       animationSpec = tween(300)
                   )
               }
           ) {
               SignUpScreen(navController)
           }

           composable("home" ,

           ) { HomeScreen(navController) }


           composable(
               route = "profile"
           ) {
               ProfileScreen(navController)
           }

           composable(
               route = "favorites"
           ) {
               FavouritesScreen(navController)
           }
          composable(
               route = "settings"
          ){
              SettingsScreen(navController)
          }
       }

}
}