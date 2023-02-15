package com.rick_and_morty.rickandmortygraphql.preseenter

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.rick_and_morty.rickandmortygraphql.preseenter.model.pagerList
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/13/2023 10:53 AM for Rick And Morty GraphQL.
 */
@ExperimentalPagerApi
@Composable
fun StartingScreen(navController: NavController) {

    val pagerState = rememberPagerState(
        initialPage = 2
    )
    LaunchedEffect(key1 = true) {
        while (true) {
            yield()
            delay(2000)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount),
                animationSpec = tween(600)
            )
        }
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            count = pagerList.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
        ) { page ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        val pageOffSet = calculateCurrentOffsetForPage(page).absoluteValue
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffSet.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffSet.coerceIn(0f, 1f)
                        )
                    }
                    .padding(top = 56.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val pagerData = pagerList[page]

                Card(
                    shape = RoundedCornerShape(16), modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(0.6f)
                ) {
                    if (pagerData.image != null && pagerData.image != "")
                        AsyncImage(model = pagerData.image, contentDescription = "Image")
                    else
                        Image(
                            painter = painterResource(id = pagerData.imageRes),
                            contentDescription = "Image"
                        )
                }
                Column(modifier = Modifier.padding(horizontal = 32.dp)) {
                    Text(
                        text = pagerData.title,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = pagerData.description,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Thin
                        ),
                        textAlign = TextAlign.Center
                    )
                }


            }
        }
    }
}
