package com.example.easybuy.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavOptions

object NavigationRoutes {
    const val SPLASH = "splash"
    const val SIGNIN = "signin"
    const val SIGNUP = "signup"
    const val HOME = "home"
    const val PROFILE = "profile"
    const val FAVORITES = "favorites"
    const val SETTINGS = "settings"

    // Routes with parameters
    const val PRODUCT_DETAILS = "product_details"
    const val PRODUCT_DETAILS_WITH_ID = "product_details/{productId}"
    const val USER_PROFILE = "user_profile"
    const val USER_PROFILE_WITH_ID = "user_profile/{userId}"
    const val SEARCH_RESULTS = "search_results"
    const val SEARCH_RESULTS_WITH_QUERY = "search_results/{query}"

    // Route builders for navigation with parameters
    fun productDetails(productId: String) = "product_details/${Uri.encode(productId)}"
    fun userProfile(userId: String) = "user_profile/${Uri.encode(userId)}"
    fun searchResults(query: String) = "search_results/${Uri.encode(query)}"
}

// Navigation arguments keys
object NavigationArgs {
    const val PRODUCT_ID = "productId"
    const val USER_ID = "userId"
    const val SEARCH_QUERY = "query"
    const val PRODUCT_DATA = "productData"
    const val USER_DATA = "userData"
}

// Navigation extension functions for type-safe navigation
object NavigationHelper {

    /**
     * Navigate to product details with type safety
     */
    fun NavController.navigateToProductDetails(
        productId: String,
        navOptions: NavOptions? = null
    ) {
        navigate(NavigationRoutes.productDetails(productId), navOptions)
    }

    /**
     * Navigate to user profile with type safety
     */
    fun NavController.navigateToUserProfile(
        userId: String,
        navOptions: NavOptions? = null
    ) {
        navigate(NavigationRoutes.userProfile(userId), navOptions)
    }

    /**
     * Navigate to search results with type safety
     */
    fun NavController.navigateToSearchResults(
        query: String,
        navOptions: NavOptions? = null
    ) {
        navigate(NavigationRoutes.searchResults(query), navOptions)
    }

    /**
     * Navigate to home and clear back stack
     */
    fun NavController.navigateToHomeAndClearStack() {
        navigate(NavigationRoutes.HOME) {
            popUpTo(0) { inclusive = true }
            launchSingleTop = true
        }
    }

    /**
     * Navigate to signin and clear back stack
     */
    fun NavController.navigateToSignInAndClearStack() {
        navigate(NavigationRoutes.SIGNIN) {
            popUpTo(0) { inclusive = true }
            launchSingleTop = true
        }
    }

    /**
     * Navigate with standard slide animation and single top behavior
     */
    fun NavController.navigateWithStandardOptions(route: String) {
        navigate(route) {
            launchSingleTop = true
            restoreState = true
        }
    }
}