package com.example.easybuy.feature.likedProducts

import android.R.attr.onClick
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.easybuy.feature.home.ProductCard


val themecolors = listOf(
    Color(0xFF1E2F8E),
    Color(0xFFEC87AA),

)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                            text = "❤️ Favourites",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 22.sp
                        ) }
                        ,
                navigationIcon = {
                        IconButton(onClick = {navController.popBackStack()}) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "BAck button",
                                tint = Color.White)
                        }



                } ,  colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent ,
                    titleContentColor = Color.White
                )
                ,
                modifier = Modifier.background(
                    Brush.horizontalGradient(
                       themecolors
                    )
                )
            )
        },

    ){ paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.LightGray)
        ) {


        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(12) { index ->
                ProductCard()
            }
        }
    }
        }

}

@Preview(showBackground = true)
@Composable
fun FavouritesScreenPreview() {
    FavouritesScreen(navController = rememberNavController())
}