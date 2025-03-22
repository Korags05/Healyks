package com.healyks.app.view.components.Carousel

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.healyks.app.data.model.DashboardItems
import com.healyks.app.data.model.DashboardResponse
import com.healyks.app.view.navigation.HealyksScreens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.min

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSlider(
    sliderContents: List<DashboardResponse>,
    modifier: Modifier = Modifier,
    navController: NavController
) {

    val infinitePages = List(1000) { it % sliderContents.size }
    val pagerState = rememberPagerState(
        pageCount = { infinitePages.size },
        initialPage = 500
    )
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        while (true) {
            delay(4500)
            scope.launch {
                val nextPage = (pagerState.currentPage + 1) % infinitePages.size
                pagerState.animateScrollToPage(
                    page = nextPage,
                    animationSpec = tween(
                        durationMillis = 800,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            HorizontalPager(
                state = pagerState,
                key = { index -> "${index}-${infinitePages[index]}"},
                pageSpacing = 8.dp,
                contentPadding = PaddingValues(horizontal = 24.dp)
            ) { index ->
                val pageOffset = (
                        (pagerState.currentPage - index) + pagerState
                            .currentPageOffsetFraction
                        ).absoluteValue

                val currentItem = sliderContents[infinitePages[index]]

                Surface(
                    modifier = Modifier
                        .graphicsLayer {
                            val scale = lerp(
                                start = 0.85f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                            scaleX = scale
                            scaleY = scale
                            alpha = lerp(
                                start = 0.5f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        }
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { navController.navigate(
                            HealyksScreens.DashboardDetailScreen.route + "/${currentItem.id}"
                        ) {
                            launchSingleTop = true
                        } }
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = currentItem.crouselImg),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        AnimatedDotsIndicator(
            totalItems = sliderContents.size,
            currentIndex = infinitePages[pagerState.currentPage] % sliderContents.size,
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}

@Composable
fun AnimatedDotsIndicator(
    totalItems: Int,
    currentIndex: Int,
    modifier: Modifier = Modifier
) {
    val maxVisibleDots = 5
    val visibleStart = if (totalItems <= maxVisibleDots) {
        0
    } else {
        val halfWindow = (maxVisibleDots - 1) / 2
        val start = currentIndex - halfWindow
        val endWindow = totalItems - maxVisibleDots
        maxOf(0, min(start, endWindow))
    }
    val visibleEnd = visibleStart + maxVisibleDots - 1

    val dotColor = MaterialTheme.colorScheme.primary
    val unselectedDotColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (index in visibleStart..visibleEnd) {
            val isSelected = index == currentIndex
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(if (isSelected) 12.dp else 8.dp)
                    .background(
                        color = if (isSelected) dotColor else unselectedDotColor,
                        shape = CircleShape
                    )
            )
        }
    }
}