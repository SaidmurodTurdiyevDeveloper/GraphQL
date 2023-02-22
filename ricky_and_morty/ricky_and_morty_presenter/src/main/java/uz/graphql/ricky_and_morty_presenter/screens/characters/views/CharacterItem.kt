package uz.graphql.ricky_and_morty_presenter.screens.characters.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import uz.graphql.ricky_and_morty_domen.model.character.CharactersListData

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 3:38 PM for Ricky And Morty.
 */
@Composable
fun CharacterItem(
    modifier: Modifier = Modifier,
    item: CharactersListData,
    onclick: (String) -> Unit
) {
    Card(modifier = modifier
        .fillMaxSize()
        .clickable {
            onclick.invoke(item.id)
        }, shape = RoundedCornerShape(4f)) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.4f)
                    .clip(shape = RoundedCornerShape(4)),
                model = item.image,
                contentDescription = "Image character",
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = item.name,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = item.status,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = item.created,
                        style = TextStyle(
                            fontSize = 16.sp,
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }

        }
    }
}