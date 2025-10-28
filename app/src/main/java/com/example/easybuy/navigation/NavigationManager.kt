package com.example.easybuy.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.easybuy.data.model.User

/**
 * NavigationManager provides centralized navigation logic and complex data passing capabilities
 */
class NavigationManager(private val navController: NavController) {

    /**
     * Navigate with simple parameters
     */
    fun navigateWithParameters(
        route: String,
        parameters: Map<String, String> = emptyMap(),
        navOptions: NavOptions? = null
    ) {
        val finalRoute = if (parameters.isNotEmpty()) {
            route + parameters.entries.joinToString("&", "?") { "${it.key}=${it.value}" }
        } else {
            route
        }
        navController.navigate(finalRoute, navOptions)
    }

    /**
     * Navigate to product details with comprehensive data
     */
    fun navigateToProductDetails(
        productId: String,
        productData: String? = null,
        navOptions: NavOptions? = null
    ) {
        // Save complex data to savedStateHandle if needed
        productData?.let { data ->
            navController.currentBackStackEntry?.savedStateHandle?.set(
                NavigationArgs.PRODUCT_DATA,
                data
            )
        }

        navController.navigate(
            NavigationRoutes.productDetails(productId),
            navOptions
        )
    }

    /**
     * Navigate to user profile with comprehensive data
     */
    fun navigateToUserProfile(
        userId: String,
        userData: User? = null,
        navOptions: NavOptions? = null
    ) {
        // Save complex user data to savedStateHandle using bundle approach
        userData?.let { user ->
            val userBundle = Bundle().apply {
                putString("userId", user.id)
                putString("email", user.email)
                putString("displayName", user.displayName)
                putString("firstName", user.firstName)
                putString("lastName", user.lastName)
                putString("profilePictureUrl", user.profilePictureUrl)
                putString("phoneNumber", user.phoneNumber)
                putString("bio", user.bio)
                putString("location", user.location)
                putBoolean("isEmailVerified", user.isEmailVerified)
                putString("authProvider", user.authProvider)
            }
            navController.currentBackStackEntry?.savedStateHandle?.set(
                NavigationArgs.USER_DATA,
                userBundle
            )
        }

        navController.navigate(
            NavigationRoutes.userProfile(userId),
            navOptions
        )
    }

    /**
     * Navigate to search results with query and additional context
     */
    fun navigateToSearchResults(
        query: String,
        filters: Map<String, String> = emptyMap(),
        navOptions: NavOptions? = null
    ) {
        // Save filters to savedStateHandle for complex search data
        if (filters.isNotEmpty()) {
            val filtersBundle = Bundle().apply {
                filters.forEach { (key, value) ->
                    putString(key, value)
                }
            }
            navController.currentBackStackEntry?.savedStateHandle?.set(
                "search_filters",
                filtersBundle
            )
        }

        navController.navigate(
            NavigationRoutes.searchResults(query),
            navOptions
        )
    }

    /**
     * Navigate back with result data
     */
    fun navigateBackWithResult(key: String, data: Any) {
        navController.previousBackStackEntry?.savedStateHandle?.set(key, data)
        navController.popBackStack()
    }

    /**
     * Navigate to home and clear entire back stack
     */
    fun navigateToHomeAndClearStack() {
        navController.navigate(NavigationRoutes.HOME) {
            popUpTo(0) { inclusive = true }
            launchSingleTop = true
        }
    }

    /**
     * Navigate to signin and clear entire back stack
     */
    fun navigateToSignInAndClearStack() {
        navController.navigate(NavigationRoutes.SIGNIN) {
            popUpTo(0) { inclusive = true }
            launchSingleTop = true
        }
    }
}

/**
 * Extension functions for easier data retrieval in screens
 */
object NavigationDataHelper {

    /**
     * Get string argument safely
     */
    fun NavBackStackEntry.getStringArg(key: String, defaultValue: String = ""): String {
        return arguments?.getString(key) ?: defaultValue
    }

    /**
     * Get User data from savedStateHandle using Bundle approach
     */
    fun SavedStateHandle.getUserData(): User? {
        val userBundle = get<Bundle>(NavigationArgs.USER_DATA)
        return userBundle?.let { bundle ->
            try {
                User(
                    id = bundle.getString("userId", ""),
                    email = bundle.getString("email", ""),
                    displayName = bundle.getString("displayName", ""),
                    firstName = bundle.getString("firstName", ""),
                    lastName = bundle.getString("lastName", ""),
                    profilePictureUrl = bundle.getString("profilePictureUrl", ""),
                    phoneNumber = bundle.getString("phoneNumber", ""),
                    bio = bundle.getString("bio", ""),
                    location = bundle.getString("location", ""),
                    isEmailVerified = bundle.getBoolean("isEmailVerified", false),
                    authProvider = bundle.getString("authProvider", "")
                )
            } catch (e: Exception) {
                null
            }
        }
    }

    /**
     * Get search filters from savedStateHandle using Bundle approach
     */
    fun SavedStateHandle.getSearchFilters(): Map<String, String> {
        val filtersBundle = get<Bundle>("search_filters")
        return filtersBundle?.let { bundle ->
            val filters = mutableMapOf<String, String>()
            bundle.keySet().forEach { key ->
                bundle.getString(key)?.let { value ->
                    filters[key] = value
                }
            }
            filters
        } ?: emptyMap()
    }

    /**
     * Get product data from savedStateHandle
     */
    fun SavedStateHandle.getProductData(): String? {
        return get<String>(NavigationArgs.PRODUCT_DATA)
    }

    /**
     * Observe navigation result
     */
    @Composable
    fun <T> SavedStateHandle.observeNavigationResult(
        key: String,
        onResult: (T) -> Unit
    ) {
        val result = remember(key) { get<T>(key) }
        result?.let {
        onResult(it)
            remove<T>(key) // Clear after consumption
        }
    }
}

/**
 * Composable helper to create NavigationManager instance
 */
@Composable
fun rememberNavigationManager(navController: NavController): NavigationManager {
    return remember(navController) { NavigationManager(navController) }
}