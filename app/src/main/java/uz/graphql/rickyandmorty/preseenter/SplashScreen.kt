package uz.graphql.rickyandmorty.preseenter

import android.app.Activity
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rick_and_morty.common_utills.other.extention.showToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import uz.graphql.common_utills.activity.Activities
import uz.graphql.rickyandmorty.preseenter.viewModel.SplashViewModel
import uz.graphql.common_utills.navigator.Navigator
import uz.graphql.rickyandmorty.navigator.Screens

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/13/2023 10:52 AM for Rick And Morty GraphQL.
 */
@Composable
fun SplashScreen(
    navController: NavController,
    provider: Navigator.Provider,
    activity: Activity,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val state = viewModel.connectedInternet.value

    var loadingState by remember {
        mutableStateOf(0f)
    }

    val loadingAnimation by animateFloatAsState(
        targetValue = loadingState,
        tween(durationMillis = 1000, easing = LinearOutSlowInEasing),
        finishedListener = {
            loadingState = 1f
        }
    )
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                SplashViewModel.SplashUiEvent.Loading -> {
                    if (state.hasNetwork)
                        loadingState = 0.75f
                }
                SplashViewModel.SplashUiEvent.OpenHome -> {
                    runCatching {
                        provider.getActivities(Activities.RickyAndMortyActivity).navigate(activity)
                    }.onSuccess {
                        delay(100)
                        activity.finish()
                    }.onFailure {
                        activity.showToast(it.message ?: "Unknown error!")
                    }
                }
                SplashViewModel.SplashUiEvent.OpenStarting -> {
                    navController.navigate(Screens.StartingScreen.route)
                }
                SplashViewModel.SplashUiEvent.ShowError -> {
                    activity.showToast(state.error ?: "Unknown error!")
                }
            }
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            if (!state.hasNetwork)
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = state.error ?: "Network has not connected",
                        style = TextStyle(fontSize = 18.sp),
                        modifier = Modifier.fillMaxWidth(0.7f)
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    IconButton(onClick = {
                        viewModel.start()
                    }) {
                        Icon(imageVector = Icons.Rounded.Refresh, contentDescription = "Icon refresg")
                    }
                }
            else
                LinearProgressIndicator(progress = loadingAnimation)
        }
    }
}