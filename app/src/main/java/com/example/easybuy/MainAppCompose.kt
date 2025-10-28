package com.example.easybuy

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.easybuy.feature.Auth.signin.SignInScreen
import com.example.easybuy.feature.Auth.signup.SignUpScreen
import com.example.easybuy.Splash.SplashScreen
import com.example.easybuy.feature.conclusion.ProfileScreen
import com.example.easybuy.feature.home.HomeScreen
import com.example.easybuy.feature.likedProducts.FavouritesScreen
import com.example.easybuy.feature.settings.SettingsScreen
import com.example.easybuy.navigation.NavigationRoutes
import com.example.easybuy.navigation.NavigationArgs

@Composable
fun EasyBuyApp() {
    Surface(modifier = Modifier.fillMaxSize()) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = NavigationRoutes.SPLASH) {

            composable(
                route = NavigationRoutes.SPLASH,
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) }
            ) {
                SplashScreen(navController)
            }

            composable(
                route = NavigationRoutes.SIGNIN,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(300)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(300)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(300)
                    )
                }
            ) {
                SignInScreen(navController)
            }

            composable(
                route = NavigationRoutes.SIGNUP,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(300)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(300)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(300)
                    )
                }
            ) {
                SignUpScreen(navController)
            }

            composable(
                route = NavigationRoutes.HOME,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(300)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(300)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(300)
                    )
                }
            ) {
                HomeScreen(navController)
            }

            composable(
                route = NavigationRoutes.PROFILE,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(300)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(300)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(300)
                    )
                }
            ) {
                ProfileScreen(navController)
            }

            composable(
                route = NavigationRoutes.FAVORITES,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(300)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(300)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(300)
                    )
                }
            ) {
                FavouritesScreen(navController)
            }

            composable(
                route = NavigationRoutes.SETTINGS,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(300)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(300)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(300)
                    )
                }
            ) {
                SettingsScreen(navController)
            }

            composable(
                route = NavigationRoutes.PRODUCT_DETAILS_WITH_ID,
                arguments = listOf(
                    navArgument(NavigationArgs.PRODUCT_ID) {
                        type = NavType.StringType
                        nullable = false
                    }
                ),
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(300)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(300)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(300)
                    )
                }
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString(NavigationArgs.PRODUCT_ID) ?: ""
                // ProductDetailsScreen(navController, productId) // Uncomment when screen is implemented
                // For now, navigate back to home as placeholder
                ProfileScreen(navController) // Temporary placeholder
            }

            composable(
                route = NavigationRoutes.USER_PROFILE_WITH_ID,
                arguments = listOf(
                    navArgument(NavigationArgs.USER_ID) {
                        type = NavType.StringType
                        nullable = false
                    }
                ),
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(300)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(300)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(300)
                    )
                }
            ) { backStackEntry ->
                val userId = backStackEntry.arguments?.getString(NavigationArgs.USER_ID) ?: ""
                // UserProfileScreen(navController, userId) // Uncomment when screen is implemented
                // For now, navigate to profile as placeholder
                ProfileScreen(navController) // Temporary placeholder
            }

            composable(
                route = NavigationRoutes.SEARCH_RESULTS_WITH_QUERY,
                arguments = listOf(
                    navArgument(NavigationArgs.SEARCH_QUERY) {
                        type = NavType.StringType
                        nullable = false
                        defaultValue = ""
                    }
                ),
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(300)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(300)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(300)
                    )
                }
            ) { backStackEntry ->
                val searchQuery =
                    backStackEntry.arguments?.getString(NavigationArgs.SEARCH_QUERY) ?: ""
                // SearchResultsScreen(navController, searchQuery) // Uncomment when screen is implemented
                // For now, navigate to home as placeholder
                HomeScreen(navController) // Temporary placeholder
            }
        }
    }
}