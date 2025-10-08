package com.example.easybuy.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Settings",
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack()}) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1A1A1A),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFF121212)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            // Account Section
            SettingsSection(title = "Account") {
                SettingsItem(
                    icon = Icons.Default.Person,
                    title = "Profile",
                    subtitle = "Manage your profile information",
                    onClick = { }
                )
                SettingsItem(
                    icon = Icons.Default.Email,
                    title = "Email",
                    subtitle = "user@example.com",
                    onClick = { }
                )
                SettingsItem(
                    icon = Icons.Default.Lock,
                    title = "Privacy & Security",
                    subtitle = "Password, 2FA, and privacy settings",
                    onClick = { }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Preferences Section
            SettingsSection(title = "Preferences") {
                var darkMode by remember { mutableStateOf(true) }
                SettingsSwitchItem(
                    icon = Icons.Default.DarkMode,
                    title = "Dark Mode",
                    subtitle = "Enable dark theme",
                    checked = darkMode,
                    onCheckedChange = { darkMode = it }
                )

                var notifications by remember { mutableStateOf(true) }
                SettingsSwitchItem(
                    icon = Icons.Default.Notifications,
                    title = "Notifications",
                    subtitle = "Enable push notifications",
                    checked = notifications,
                    onCheckedChange = { notifications = it }
                )

                SettingsItem(
                    icon = Icons.Default.Language,
                    title = "Language",
                    subtitle = "English (US)",
                    onClick = { }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // App Section
            SettingsSection(title = "App") {
                SettingsItem(
                    icon = Icons.Default.Storage,
                    title = "Storage",
                    subtitle = "Manage app storage and cache",
                    onClick = { }
                )
                SettingsItem(
                    icon = Icons.Default.Info,
                    title = "About",
                    subtitle = "Version 1.0.0",
                    onClick = { }
                )
                SettingsItem(
                    icon = Icons.Default.Description,
                    title = "Terms & Conditions",
                    subtitle = "Read our terms of service",
                    onClick = { }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Support Section
            SettingsSection(title = "Support") {
                SettingsItem(
                    icon = Icons.Default.Help,
                    title = "Help & Support",
                    subtitle = "Get help with the app",
                    onClick = { }
                )
                SettingsItem(
                    icon = Icons.Default.Feedback,
                    title = "Send Feedback",
                    subtitle = "Share your thoughts with us",
                    onClick = { }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Logout Button
            Button(
                onClick = { /* Handle logout */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFDC3545)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Logout, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Logout", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF999999),
            modifier = Modifier.padding(bottom = 12.dp, start = 4.dp)
        )

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFF1E1E1E)
        ) {
            Column(
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                content()
            }
        }
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(10.dp),
            color = Color(0xFF2A2A2A)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF6B9DFF),
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
            Text(
                text = subtitle,
                fontSize = 13.sp,
                color = Color(0xFF999999),
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Color(0xFF666666),
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun SettingsSwitchItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(10.dp),
            color = Color(0xFF2A2A2A)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF6B9DFF),
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
            Text(
                text = subtitle,
                fontSize = 13.sp,
                color = Color(0xFF999999),
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF6B9DFF),
                uncheckedThumbColor = Color(0xFF999999),
                uncheckedTrackColor = Color(0xFF3A3A3A)
            )
        )
    }
}
@Preview(showSystemUi = true)
@Composable
fun PreviewSignUpScreen() {
   SettingsScreen(navController = rememberNavController())
}