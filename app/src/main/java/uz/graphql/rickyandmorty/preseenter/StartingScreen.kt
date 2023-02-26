package uz.graphql.rickyandmorty.preseenter

import android.app.Activity
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.rick_and_morty.common_utills.other.extention.showToast
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import uz.graphql.common_utills.activity.Activities
import uz.graphql.rickyandmorty.preseenter.model.pagerList
import uz.graphql.common_utills.navigator.Navigator
import uz.graphql.rickyandmorty.preseenter.viewModel.ViewModelStarting
import kotlin.math.absoluteValue

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/13/2023 10:53 AM for Rick And Morty GraphQL.
 */
@ExperimentalPagerApi
@Composable
fun StartingScreen(
    navController: NavController,
    provider: Navigator.Provider,
    activity: Activity,
    viewModel: ViewModelStarting = hiltViewModel()
) {

    val pagerState = rememberPagerState(
        initialPage = 0
    )
    var movePage by remember {
        mutableStateOf(0)
    }
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                ViewModelStarting.EventViewModelStarting.OpenHome -> {
                    runCatching {
                        provider.getActivities(Activities.RickyAndMortyActivity).navigate(activity)
                    }.onSuccess {
                        delay(100)
                        activity.finish()
                    }.onFailure {
                        activity.showToast("Wrong!")
                    }
                }
            }
        }
    }
    LaunchedEffect(key1 = movePage) {
        if (2 != pagerState.currentPage) {
            if (movePage != 0) {
                yield()
                tween<Float>(600)
                pagerState.animateScrollToPage(
                    page = (pagerState.currentPage + 1) % (pagerState.pageCount)
                )
            }
        } else {
            viewModel.open(ViewModelStarting.EventUiStarting.OpenHome)
        }
    }


    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            movePage++
        }) {
            if (pagerState.currentPage != 2)
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Icon")
            else
                Icon(imageVector = Icons.Default.Done, contentDescription = "Icon")

        }
    }) { initializing ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(initializing)
        ) {
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
                        .padding(top = 16.dp), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val pagerData = pagerList[page]

                    Card(
                        shape = RoundedCornerShape(8), modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .fillMaxHeight(0.6f)
                    ) {
                        if (pagerData.image != null && pagerData.image != "")
                            AsyncImage(model = pagerData.image, contentDescription = "Image", contentScale = ContentScale.Crop)
                        else
                            Image(
                                painter = painterResource(id = pagerData.imageRes),
                                contentDescription = "Image"
                            )
                    }
                    Column(modifier = Modifier.padding(horizontal = 32.dp)) {
                        Spacer(modifier = Modifier.height(16.dp))
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
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        )
                    }
                }
            }
        }

    }
}
