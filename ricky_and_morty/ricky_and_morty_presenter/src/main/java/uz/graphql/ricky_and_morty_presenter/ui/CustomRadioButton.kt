package uz.graphql.ricky_and_morty_presenter.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/25/2023 5:35 PM for Ricky And Morty.
 */
@Composable
fun RadiGroupButton(
    modifier: Modifier = Modifier,
    isVertically: Boolean = false,
    items: List<String>,
    itemClick: (String) -> Unit,
    title: String = "",
    selectIndex: Int = items.size - 1
) {
    if (isVertically) {
        Column(
            modifier = modifier
                .fillMaxSize(), horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))
            LoadRadioButtonItems(items = items, itemClick = itemClick, selectIndex = selectIndex)
        }
    } else {
        Column(modifier = modifier.fillMaxSize()) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                LoadRadioButtonItems(items = items, itemClick = itemClick, selectIndex = selectIndex)
            }
        }
    }

}

@Composable
private fun LoadRadioButtonItems(
    items: List<String>,
    itemClick: (String) -> Unit,
    selectIndex: Int = items.size - 1
) {
    var selectedIndex by remember {
        mutableStateOf(selectIndex)
    }
    items.forEachIndexed { index, item ->
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedIndex == index, onClick = {
                    Log.d("TTTTDD", item)
                    itemClick.invoke(item)
                    selectedIndex = index
                },
                modifier = Modifier.size(28.dp)
            )
            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(fontWeight = FontWeight.Normal, color = if (isSystemInDarkTheme()) Color.White else Color.DarkGray)
                ) {
                    if (item.isBlank())
                        append("All")
                    else
                        append(item)
                }
            }
            ClickableText(
                text = annotatedString,
                onClick = {
                    Log.d("TTTTDD", item)
                    itemClick.invoke(item)
                    selectedIndex = index
                }
            )
        }
    }

}