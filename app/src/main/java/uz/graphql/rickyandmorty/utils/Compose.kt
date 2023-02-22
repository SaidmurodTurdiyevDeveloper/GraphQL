package uz.graphql.rickyandmorty.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 2:58 PM for Ricky And Morty.
 */

@Composable
fun setStatusBarColor(
    systemUiController: SystemUiController = rememberSystemUiController(),
    lightThemeColor: Color = Color.White,
    darkThemeColor: Color = Color.Black
) {
    if (isSystemInDarkTheme()) {
        systemUiController.setSystemBarsColor(
            color = darkThemeColor
        )
    } else {
        systemUiController.setSystemBarsColor(
            color = lightThemeColor
        )
    }
}