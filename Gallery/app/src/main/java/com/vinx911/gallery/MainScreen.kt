package com.vinx911.gallery

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val images = remember {
        mutableStateListOf(
            "https://images.pexels.com/photos/1450082/pexels-photo-1450082.jpeg?auto=compress&cs=tinysrgb&fm=jpg&w=640&h=960",
            "https://images.pexels.com/photos/1366919/pexels-photo-1366919.jpeg?auto=compress&cs=tinysrgb&fm=jpg&w=640&h=960",
            "https://images.pexels.com/photos/1743165/pexels-photo-1743165.jpeg?auto=compress&cs=tinysrgb&fm=jpg&w=640&h=960",
            "https://images.pexels.com/photos/2437291/pexels-photo-2437291.jpeg?auto=compress&cs=tinysrgb&fm=jpg&w=640&h=960",
            "https://images.pexels.com/photos/1670187/pexels-photo-1670187.jpeg?auto=compress&cs=tinysrgb&fm=jpg&w=640&h=960",
            "https://images.pexels.com/photos/1266810/pexels-photo-1266810.jpeg?auto=compress&cs=tinysrgb&fm=jpg&w=640&h=960",
            "https://images.pexels.com/photos/2832034/pexels-photo-2832034.jpeg?auto=compress&cs=tinysrgb&fm=jpg&w=640&h=960",
            "https://images.pexels.com/photos/1743366/pexels-photo-1743366.jpeg?auto=compress&cs=tinysrgb&fm=jpg&w=640&h=960",
            "https://images.pexels.com/photos/4555468/pexels-photo-4555468.jpeg?auto=compress&cs=tinysrgb&fm=jpg&w=640&h=960",
            "https://images.pexels.com/photos/4552789/pexels-photo-4552789.jpeg?auto=compress&cs=tinysrgb&fm=jpg&w=640&h=960",
        )
    }
    val imageCount = images.count()
    val dummyPageCount = Int.MAX_VALUE

    val pagerState = rememberPagerState(
        initialPage = (dummyPageCount / imageCount / 2) * imageCount
    )
    val matrix = remember { ColorMatrix() }

    Scaffold(modifier = Modifier.padding(vertical = 48.dp)) {
        HorizontalPager(
            pageCount = dummyPageCount,
            state = pagerState
        ) { dummyIndex ->
            val index = dummyIndex % imageCount
            val currentIndex = pagerState.currentPage % imageCount
            val pagerOffset = (currentIndex - index) + pagerState.currentPageOffsetFraction
            val imageSize by animateFloatAsState(
                targetValue = if (pagerOffset != 0.0f) 0.75f else 1f,
                animationSpec = tween(durationMillis = 300)
            )

            LaunchedEffect(key1 = imageSize) {
                if (pagerOffset != 0.0f) {
                    matrix.setToSaturation(0f)
                } else {
                    matrix.setToSaturation(1f)
                }
            }

            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .graphicsLayer {
                        scaleX = imageSize
                        scaleY = imageSize
                    }
                    .clip(RoundedCornerShape(16.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(images[index])
                    .build(),
                contentDescription = "Image",
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.colorMatrix(matrix)
            )
        }
    }
}
