package uz.graphql.ricky_and_morty_presenter.screens.characters.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import uz.graphql.ricky_and_morty_domen.model.character.CharactersListData
import uz.graphql.ricky_and_morty_presenter.ui.theme.DarkSelect
import uz.graphql.ricky_and_morty_presenter.ui.theme.LightSelect
import java.time.ZonedDateTime

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 3:38 PM for Ricky And Morty.
 */
@ExperimentalFoundationApi
@Composable
fun ItemViewCharacter(
    modifier: Modifier = Modifier,
    item: CharactersListData,
    onClick: (String) -> Unit,
    onLongClick: () -> Unit,
    onDoubleClick: () -> Unit
) {
    val selectedColor by if (isSystemInDarkTheme()) remember {
        mutableStateOf(
            DarkSelect
        )
    } else remember {
        mutableStateOf(
            LightSelect
        )
    }
    val time = try {
        ZonedDateTime.parse(item.created)
    } catch (e: Exception) {
        null
    }

    val statusColor = if (item.status == "Alive") Color.Green else if (item.status == "Dead") Color.Red else Color.LightGray

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
                }),
        shape = RoundedCornerShape(4f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(color = if (item.select)
                    selectedColor else Color.Transparent)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clip(shape = RoundedCornerShape(4)),
                model = item.image,
                contentDescription = "Image character",
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(
                        horizontal = 12.dp,
                        vertical = 2.dp,
                    )
            ) {
                Text(
                    text = item.name,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Divider(color = Color.LightGray, thickness = 1.dp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.species,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.gender,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.status,
                    fontSize = 14.sp,
                    style = TextStyle(
                        color = statusColor
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.type,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = time?.dayOfMonth.toString() + "-" + time?.monthValue.toString() + "-" + time?.year,
                    style = TextStyle(
                        fontSize = 11.sp
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}