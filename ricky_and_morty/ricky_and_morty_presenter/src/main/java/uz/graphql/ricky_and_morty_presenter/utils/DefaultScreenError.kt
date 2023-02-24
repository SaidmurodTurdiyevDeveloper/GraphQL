package uz.graphql.ricky_and_morty_presenter.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/24/2023 3:20 PM for Ricky And Morty.
 */
@Composable
fun DefaultScreenError(
    modifier: Modifier = Modifier,
    errorMessage: String,
    errorMessageColor: Color = Color.Red,
    errorTextSize: TextUnit = 16.sp,
    errorTextFontWeight: FontWeight = FontWeight.Bold,
    block:()->Unit={}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = errorMessage,
            fontSize = errorTextSize,
            color = errorMessageColor,
            fontWeight = errorTextFontWeight,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}