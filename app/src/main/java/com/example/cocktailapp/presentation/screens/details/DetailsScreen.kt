package com.example.cocktailapp.presentation.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.cocktailapp.ui.theme.DarkGradient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(viewModel: DetailsViewModel = hiltViewModel(), onBackClick: () -> Unit) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = state.cocktail?.name ?: "", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = Color.Transparent
    ) {
        paddingValues ->
        Box(modifier = Modifier.fillMaxSize()
            .background(brush = DarkGradient)
            .padding(paddingValues)
        ) {
            state.cocktail?.let { cocktail ->
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    // Cocktail Image
                    item {
                        AsyncImage(
                            model = cocktail.image,
                            contentDescription = cocktail.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                    // Cocktail Info
                    item {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = cocktail.name, style = MaterialTheme.typography.headlineMedium, color = Color.White)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = cocktail.category.orEmpty(), style = MaterialTheme.typography.titleMedium, color = Color.White.copy(alpha = 0.7f))
                            Spacer(modifier = Modifier.height(16.dp))

                            // Ingredients
                            Text(text = "Ingredients", style = MaterialTheme.typography.titleLarge, color = Color.White)
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color.White.copy(alpha = 0.5f))
                            cocktail.ingredients.forEach { item ->
                                Row {
                                    Text(text = "- ${item.name}", modifier = Modifier.weight(1f), color = Color.White)
                                    Text(text = item.measure, color = Color.White.copy(alpha = 0.7f))
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Instructions
                            Text(text = "Instructions", style = MaterialTheme.typography.titleLarge, color = Color.White)
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color.White.copy(alpha = 0.5f))
                            Text(text = cocktail.instructions.orEmpty(), style = MaterialTheme.typography.bodyLarge, color = Color.White)
                        }
                    }
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            state.error?.let {
                Text(
                    text = it,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    color = Color.Red
                )
            }
        }
    }
}
