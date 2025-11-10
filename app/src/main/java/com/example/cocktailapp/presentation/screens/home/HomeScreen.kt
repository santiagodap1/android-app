package com.example.cocktailapp.presentation.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.cocktailapp.domain.model.Ingredient
import com.example.cocktailapp.presentation.screens.components.DrinkItem
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onIngredientClick: (String) -> Unit,
    onDrinkClick: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    var dominantColors by remember { mutableStateOf<List<Color>>(emptyList()) }
    val context = LocalContext.current

    val pagerState = rememberPagerState(pageCount = { state.randomIngredients.size })
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    // Smart auto-scroll effect
    LaunchedEffect(key1 = isDragged, key2 = pagerState.pageCount) {
        if (!isDragged && pagerState.pageCount > 1) {
            while (true) {
                delay(4000)
                val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    LaunchedEffect(state.randomIngredients) {
        if (state.randomIngredients.isNotEmpty()) {
            val colors = List(state.randomIngredients.size) { Color.Black }.toMutableList()
            coroutineScope {
                state.randomIngredients.forEachIndexed { index, ingredient ->
                    launch {
                        val imageUrl = "https://www.thecocktaildb.com/images/ingredients/${ingredient.name}-Medium.png"
                        val request = ImageRequest.Builder(context).data(imageUrl).allowHardware(false).build()
                        val result = (context.imageLoader.execute(request) as? SuccessResult)?.drawable
                        result?.let {
                            Palette.from(it.toBitmap()).generate()?.dominantSwatch?.rgb?.let { colorValue ->
                                colors[index] = Color(colorValue)
                            }
                        }
                    }
                }
            }
            dominantColors = colors
        }
    }

    val interpolatedColor by remember(pagerState.currentPage, pagerState.currentPageOffsetFraction) {
        derivedStateOf {
            if (dominantColors.size > 1) {
                val currentPage = pagerState.currentPage
                val targetPage = pagerState.targetPage
                val offset = pagerState.currentPageOffsetFraction.absoluteValue

                val startColor = dominantColors.getOrElse(currentPage) { Color.Black }
                val endColor = dominantColors.getOrElse(targetPage) { Color.Black }

                lerp(startColor, endColor, offset)
            } else if (dominantColors.isNotEmpty()) {
                dominantColors.first()
            } else {
                Color.Black
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(interpolatedColor, Color.Black)
                )
            )
    ) {
        if (!state.isLoading) {
            LazyColumn(contentPadding = PaddingValues(top = 48.dp, bottom = 100.dp)) {
                // Random Ingredients Carousel
                if (state.randomIngredients.isNotEmpty()) {
                    item {
                        Text(
                            text = "Random Ingredients",
                            style = typography.titleLarge,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalPager(
                            state = pagerState,
                            contentPadding = PaddingValues(horizontal = 32.dp),
                            pageSpacing = 16.dp
                        ) { page ->
                            IngredientItem(
                                ingredient = state.randomIngredients[page],
                                onClick = { onIngredientClick(state.randomIngredients[page].name) }
                            )
                        }
                        Spacer(modifier = Modifier.height(40.dp))
                    }
                }

                // Categories with Drinks
                items(state.categoriesWithDrinks) { categoryWithDrinks ->
                    Text(
                        text = categoryWithDrinks.category,
                        style = typography.titleLarge,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(categoryWithDrinks.drinks) { drink ->
                            DrinkItem(
                                drink = drink, 
                                modifier = Modifier.width(150.dp),
                                onClick = { onDrinkClick(drink.id) }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
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
                color = Color.White
            )
        }
    }
}

@Composable
fun IngredientItem(ingredient: Ingredient, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = "https://www.thecocktaildb.com/images/ingredients/${ingredient.name}-Medium.png",
            contentDescription = ingredient.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp), // Increased height
            contentScale = ContentScale.Fit
        )
        Text(
            text = ingredient.name,
            modifier = Modifier.padding(top = 8.dp),
            textAlign = TextAlign.Center,
            style = typography.titleMedium,
            color = Color.White
        )
    }
}

