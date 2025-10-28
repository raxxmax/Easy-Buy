package com.example.easybuy.data.repository

import android.util.Log
import com.example.easybuy.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    private val usersCollection = firestore.collection("users")
    private val TAG = "UserRepository"

    suspend fun createOrUpdateUserFromFirebaseAuth(firebaseUser: FirebaseUser): Result<User> {
        return try {
            Log.d(TAG, "Creating/updating user from Firebase Auth: ${firebaseUser.email}")

            // Parse display name into first and last name
            val displayName = firebaseUser.displayName ?: ""
            val nameParts = displayName.split(" ", limit = 2)
            val firstName = nameParts.getOrElse(0) { "" }
            val lastName = nameParts.getOrElse(1) { "" }

            // Determine auth provider
            val authProvider = when {
                firebaseUser.providerData.any { it.providerId == "google.com" } -> "google"
                firebaseUser.providerData.any { it.providerId == "password" } -> "email"
                else -> "unknown"
            }

            val user = User(
                id = firebaseUser.uid,
                email = firebaseUser.email ?: "",
                displayName = displayName,
                firstName = firstName,
                lastName = lastName,
                profilePictureUrl = firebaseUser.photoUrl?.toString() ?: "",
                phoneNumber = firebaseUser.phoneNumber ?: "",
                isEmailVerified = firebaseUser.isEmailVerified,
                authProvider = authProvider,
                createdAt = Timestamp.now(),
                updatedAt = Timestamp.now()
            )

            // Check if user already exists
            val existingUser = getUserById(firebaseUser.uid)
            if (existingUser != null) {
                // Update existing user with new information
                val updatedUser = existingUser.copy(
                    email = user.email,
                    displayName = user.displayName,
                    firstName = user.firstName,
                    lastName = user.lastName,
                    profilePictureUrl = user.profilePictureUrl,
                    phoneNumber = user.phoneNumber,
                    isEmailVerified = user.isEmailVerified,
                    updatedAt = Timestamp.now()
                )

                usersCollection.document(firebaseUser.uid).set(updatedUser).await()
                Log.d(TAG, "User updated successfully: ${updatedUser.email}")
                Result.success(updatedUser)
            } else {
                // Create new user
                usersCollection.document(firebaseUser.uid).set(user).await()
                Log.d(TAG, "User created successfully: ${user.email}")
                Result.success(user)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error creating/updating user", e)
            Result.failure(e)
        }
    }

    suspend fun getUserById(userId: String): User? {
        return try {
            Log.d(TAG, "Fetching user by ID: $userId")
            val document = usersCollection.document(userId).get().await()
            if (document.exists()) {
                val user = document.toObject(User::class.java)
                Log.d(TAG, "User found: ${user?.email}")
                user
            } else {
                Log.d(TAG, "User not found with ID: $userId")
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching user by ID", e)
            null
        }
    }

    suspend fun getCurrentUser(): User? {
        val currentFirebaseUser = auth.currentUser
        return if (currentFirebaseUser != null) {
            getUserById(currentFirebaseUser.uid)
        } else {
            Log.d(TAG, "No authenticated Firebase user")
            null
        }
    }

    fun getCurrentUserFlow(): Flow<User?> = flow {
        val currentFirebaseUser = auth.currentUser
        if (currentFirebaseUser != null) {
            emit(getCurrentUser())
        } else {
            emit(null)
        }
    }

    suspend fun updateUser(user: User): Result<User> {
        return try {
            Log.d(TAG, "Updating user: ${user.email}")
            val updatedUser = user.copy(updatedAt = Timestamp.now())
            usersCollection.document(user.id).set(updatedUser).await()
            Log.d(TAG, "User updated successfully: ${user.email}")
            Result.success(updatedUser)
        } catch (e: Exception) {
            Log.e(TAG, "Error updating user", e)
            Result.failure(e)
        }
    }

    suspend fun updateUserProfile(
        userId: String,
        displayName: String? = null,
        firstName: String? = null,
        lastName: String? = null,
        bio: String? = null,
        location: String? = null,
        phoneNumber: String? = null
    ): Result<User> {
        return try {
            val existingUser = getUserById(userId)
            if (existingUser != null) {
                val updatedUser = existingUser.copy(
                    displayName = displayName ?: existingUser.displayName,
                    firstName = firstName ?: existingUser.firstName,
                    lastName = lastName ?: existingUser.lastName,
                    bio = bio ?: existingUser.bio,
                    location = location ?: existingUser.location,
                    phoneNumber = phoneNumber ?: existingUser.phoneNumber,
                    updatedAt = Timestamp.now()
                )
                updateUser(updatedUser)
            } else {
                Log.e(TAG, "User not found for profile update: $userId")
                Result.failure(Exception("User not found"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error updating user profile", e)
            Result.failure(e)
        }
    }

    suspend fun deleteUser(userId: String): Result<Unit> {
        return try {
            Log.d(TAG, "Deleting user: $userId")
            usersCollection.document(userId).delete().await()
            Log.d(TAG, "User deleted successfully: $userId")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting user", e)
            Result.failure(e)
        }
    }
}