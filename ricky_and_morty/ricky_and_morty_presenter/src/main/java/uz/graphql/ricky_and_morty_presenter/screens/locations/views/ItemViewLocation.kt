@file:OptIn(ExperimentalFoundationApi::class)

package uz.graphql.ricky_and_morty_presenter.screens.locations.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.graphql.ricky_and_morty_domen.model.location.LocationsListData
import uz.graphql.ricky_and_morty_presenter.ui.theme.DarkSelect
import uz.graphql.ricky_and_morty_presenter.ui.theme.LightSelect
import java.time.ZonedDateTime

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 3:46 PM for Ricky And Morty.
 */
@Composable
fun ItemViewLocation(
    item: LocationsListData,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
    onLongClick: () -> Unit,
    onDoubleClick: () -> Unit
) {
    val time = try {
        ZonedDateTime.parse(item.created)
    } catch (e: Exception) {
        null
    }
    val selectedColor by if (isSystemInDarkTheme()) remember {
        mutableStateOf(
            DarkSelect
        )
    } else remember {
        mutableStateOf(
            LightSelect
        )
    }
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
            .combinedClickable(
                enabled = true,
                onClick = {
                    onClick.invoke(item.id)
                },
                onLongClick = {
                    onLongClick.invoke()
                },
                onDoubleClick = {
                    onDoubleClick.invoke()
                }), shape = RoundedCornerShape(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = if (item.select)
                    selectedColor else Color.Transparent)
                .padding(vertical = 16.dp, horizontal = 8.dp)
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Name", fontSize = 16.sp)
                Text(text = item.name, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Dimension", fontSize = 16.sp)
                Text(text = item.dimension, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Type", fontSize = 16.sp)
                Text(text = item.type, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Created", fontSize = 16.sp)
                Text(text = time?.dayOfMonth.toString() + "-" + time?.monthValue.toString() + "-" + time?.year, fontSize = 16.sp)
            }
        }
    }
}