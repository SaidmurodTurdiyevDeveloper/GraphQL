package uz.graphql.ricky_and_morty_presenter.screens.locations.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import uz.graphql.ricky_and_morty_domen.model.location.LocationListWithDetailsData
import uz.graphql.ricky_and_morty_domen.model.location.LocationWithDetailsResidentData
import java.time.ZonedDateTime
import kotlin.random.Random

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 3:47 PM for Ricky And Morty.
 */
@Composable
fun ItemViewLocationDetails(
    item: LocationListWithDetailsData,
    modifier: Modifier = Modifier,
    openResident: (String) -> Unit,
    openLocation: (String) -> Unit
) {
    val time = try {
        ZonedDateTime.parse(item.created)
    } catch (e: Exception) {
        null
    }
    val randomColor = Color(
        Random.nextFloat(),
        Random.nextFloat(),
        Random.nextFloat(),
        0.2f
    )
    Card(
        modifier = modifier
            .fillMaxSize()
            .clickable {
                openLocation.invoke(item.id)
            }
            .padding(8.dp), shape = RoundedCornerShape(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = randomColor
                )
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Name", fontSize = 18.sp)
                Text(text = item.name, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Divider()
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Dimension", fontSize = 18.sp)
                Text(text = item.dimension, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Divider()
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Type", fontSize = 18.sp)
                Text(text = item.type, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Divider()
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Residents", fontSize = 18.sp, modifier = Modifier.padding(start = 12.dp))
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow {
                items(item.residents) { resident ->
                    val createdData = try {
                        ZonedDateTime.parse(resident.created)
                    } catch (e: Exception) {
                        null
                    }
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .width(150.dp)
                            .clickable {
                                openResident.invoke(resident.id)
                            }, shape = RoundedCornerShape(4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .background(color = randomColor)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                        {
                            AsyncImage(
                                modifier = Modifier
                                    .height(150.dp)
                                    .fillMaxWidth()
                                    .clip(shape = RoundedCornerShape(4)),
                                model = resident.image,
                                contentDescription = "Image character",
                                contentScale = ContentScale.Crop
                            )
                            Text(text = resident.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = resident.status, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = createdData?.dayOfMonth.toString() + "-" + createdData?.monthValue.toString() + "-" + createdData?.year, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Divider()
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Created", fontSize = 18.sp)
                Text(text = time?.dayOfMonth.toString() + "-" + time?.monthValue.toString() + "-" + time?.year, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}