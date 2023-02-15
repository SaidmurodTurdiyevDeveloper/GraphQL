package com.rick_and_morty.rickandmortygraphql.preseenter

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rick_and_morty.common_utills.activity.Activities
import com.rick_and_morty.common_utills.navigator.Navigator
import com.rick_and_morty.presenter.GoToRickyAndMortyActivity
import com.rick_and_morty.rickandmortygraphql.navigator.NavigatorProvider
import com.rick_and_morty.rickandmortygraphql.preseenter.viewModel.SplashViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/13/2023 10:52 AM for Rick And Morty GraphQL.
 */
@Composable
fun SplashScreen(navController: NavController,provider:NavigatorProvider, activity: Activity, viewModel: SplashViewModel = hiltViewModel()) {
    var showError by remember {
        mutableStateOf(false)
    }
    var errorText by remember {
        mutableStateOf("")
    }
    LaunchedEffect(key1 = true) {
        viewModel.openHome.collectLatest { isOpen ->
            if (isOpen) {
                runCatching {
                    provider.getActivities(Activities.RickyAndMortyActivity).navigate(activity)
                }.onSuccess {
                    delay(100)
                    activity.finish()
                }.onFailure {
                    showError = true
                    errorText = it.message ?: "Unknown error!"
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
            if (showError)
                Text(
                    text = errorText,
                    style = TextStyle(fontSize = 18.sp),
                    modifier = Modifier.fillMaxWidth(0.7f)
                )
            else
                LinearProgressIndicator(progress = 0.7f)
        }
    }
}