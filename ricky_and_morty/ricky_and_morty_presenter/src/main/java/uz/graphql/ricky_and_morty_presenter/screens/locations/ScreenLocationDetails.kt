package uz.graphql.ricky_and_morty_presenter.screens.locations

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.collectLatest
import uz.graphql.ricky_and_morty_domen.model.location.LocationDetailsData
import uz.graphql.ricky_and_morty_presenter.navigation.ScreenRoutes
import uz.graphql.ricky_and_morty_presenter.ui.theme.White
import uz.graphql.ricky_and_morty_presenter.utils.DefaultScreenError
import uz.graphql.ricky_and_morty_presenter.utils.DefaultScreenLoading
import uz.graphql.ricky_and_morty_presenter.vieewModels.locations.ViewModelLocationDetails
import java.time.ZonedDateTime
import kotlin.random.Random

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 3:44 PM for Ricky And Morty.
 */
@Composable
fun ScreenLocationDetails(
    navController: NavHostController = rememberNavController(),
    viewModel: ViewModelLocationDetails = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is ViewModelLocationDetails.EventViewModelLocationDetails.OpenResidents -> {
                    navController.navigate(route = ScreenRoutes.Character.CharacterDetailsScreen(event.id).route)
                }
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Location Details")
                },
                backgroundColor = if (isSystemInDarkTheme()) Color.DarkGray else White,
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Icon back")
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            if (state.isLoading)
                DefaultScreenLoading(
                    modifier = Modifier.fillMaxSize()
                )
            else if (!state.error.isNullOrBlank()) {
                DefaultScreenError(modifier = Modifier.fillMaxSize(), errorMessage = state.error) {
                    viewModel.onEvent(ViewModelLocationDetails.EventUILocationDetails.Refresh)
                }
            } else if (state.location != null) {
                LocationDetailsScreen(
                    item = state.location,
                    openResident = { data ->
                        viewModel.onEvent(ViewModelLocationDetails.EventUILocationDetails.OpenResidents(data))
                    }
                )
            } else {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "We cannot find any Location",
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun LocationDetailsScreen(
    modifier: Modifier = Modifier,
    item: LocationDetailsData,
    openResident: (String) -> Unit
) {
    val state = rememberScrollState()
    val time = try {
        ZonedDateTime.parse(item.created)
    } catch (e: Exception) {
        null
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(
                state = state,
                enabled = true
            )
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Name", fontSize = 18.sp, color = Color(
                    Random.nextFloat(),
                    Random.nextFloat(),
                    Random.nextFloat(),
                    1f
                )
            )
            Text(
                text = item.name, fontSize = 18.sp, color = Color(
                    Random.nextFloat(),
                    Random.nextFloat(),
                    Random.nextFloat(),
                    1f
                )
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Divider()
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Dimension", fontSize = 18.sp, color = Color(
                    Random.nextFloat(),
                    Random.nextFloat(),
                    Random.nextFloat(),
                    1f
                )
            )
            Text(
                text = item.dimension, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(
                    Random.nextFloat(),
                    Random.nextFloat(),
                    Random.nextFloat(),
                    1f
                )
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Divider()
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Type", fontSize = 18.sp, color = Color(
                    Random.nextFloat(),
                    Random.nextFloat(),
                    Random.nextFloat(),
                    1f
                )
            )
            Text(
                text = item.type, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(
                    Random.nextFloat(),
                    Random.nextFloat(),
                    Random.nextFloat(),
                    1f
                )
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Divider()
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Created", fontSize = 18.sp, color = Color(
                    Random.nextFloat(),
                    Random.nextFloat(),
                    Random.nextFloat(),
                    1f
                )
            )
            Text(
                text = time?.dayOfMonth.toString() + "-" + time?.monthValue.toString() + "-" + time?.year, fontSize = 18.sp,
                fontWeight = FontWeight.Bold, color = Color(
                    Random.nextFloat(),
                    Random.nextFloat(),
                    Random.nextFloat(),
                    1f
                )
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Divider()
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Residents", fontSize = 18.sp, modifier = Modifier.padding(start = 12.dp), color = Color(
                Random.nextFloat(),
                Random.nextFloat(),
                Random.nextFloat(),
                1f
            )
        )
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
                        Text(
                            text = resident.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(
                                Random.nextFloat(),
                                Random.nextFloat(),
                                Random.nextFloat(),
                                1f
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = resident.status, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(
                                Random.nextFloat(),
                                Random.nextFloat(),
                                Random.nextFloat(),
                                1f
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = createdData?.dayOfMonth.toString() + "-" + createdData?.monthValue.toString() + "-" + createdData?.year,
                            fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(
                                Random.nextFloat(),
                                Random.nextFloat(),
                                Random.nextFloat(),
                                1f
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}