package com.example.easybuy.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId
    val id: String = "",
    val email: String = "",
    val displayName: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val profilePictureUrl: String = "",
    val phoneNumber: String = "",
    val bio: String = "",
    val location: String = "",
    val isEmailVerified: Boolean = false,
    val authProvider: String = "", // "google", "email", etc.
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now(),
    val preferences: UserPreferences = UserPreferences()
)

data class UserPreferences(
    val notificationsEnabled: Boolean = true,
    val emailNotificationsEnabled: Boolean = true,
    val pushNotificationsEnabled: Boolean = true,
    val privacyLevel: String = "public" // "public", "friends", "private"
)