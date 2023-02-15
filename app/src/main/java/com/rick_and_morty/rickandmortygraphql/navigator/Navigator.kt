package com.rick_and_morty.rickandmortygraphql.navigator

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.rick_and_morty.rickandmortygraphql.preseenter.SplashScreen
import com.rick_and_morty.rickandmortygraphql.preseenter.StartingScreen

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/13/2023 6:04 PM for Rick And Morty GraphQL.
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun Navigator(activity: Activity, provider: NavigatorProvider) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.SplashScreen.route)
    {
        composable(route = Screens.SplashScreen.route) {
            SplashScreen(
                navController = navController,
                activity = activity,
                provider = provider
            )
        }
        composable(route = Screens.StartingScreen.route) {
            StartingScreen(navController = navController)
        }
    }
}
