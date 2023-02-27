package uz.graphql.ricky_and_morty_presenter.screens.episodes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.collectLatest
import uz.graphql.ricky_and_morty_domen.model.episode.EpisodeDetailsData
import uz.graphql.ricky_and_morty_presenter.navigation.ScreenRoutes
import uz.graphql.ricky_and_morty_presenter.ui.theme.White
import uz.graphql.ricky_and_morty_presenter.utils.DefaultScreenError
import uz.graphql.ricky_and_morty_presenter.utils.DefaultScreenLoading
import uz.graphql.ricky_and_morty_presenter.vieewModels.episodes.ViewModelEpisodeDetails
import java.time.ZonedDateTime
import kotlin.random.Random

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 3:39 PM for Ricky And Morty.
 */
@Composable
fun ScreenEpisodeDetails(
    navController: NavHostController = rememberNavController(),
    viewModel: ViewModelEpisodeDetails = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is ViewModelEpisodeDetails.EventViewModelEpisodeDetails.OpenCharacterDetailsScreen -> {
                    navController.navigate(route = ScreenRoutes.Character.CharacterDetailsScreen(event.id).route)
                }
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Episode Details")
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
                    viewModel.onEvent(ViewModelEpisodeDetails.EventUIEpisodeDetails.Refresh)
                }
            } else if (state.episode != null) {
                EpisodeDetailsScreen(
                    episode = state.episode,
                    openCharacter = { id ->
                        viewModel.onEvent(ViewModelEpisodeDetails.EventUIEpisodeDetails.OpenCharacterDetailsScreen(id))
                    }
                )
            } else {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "We cannot find any Episode",
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun EpisodeDetailsScreen(
    modifier: Modifier = Modifier,
    episode: EpisodeDetailsData,
    openCharacter: (String) -> Unit
) {
    val state = rememberScrollState()
    val time = try {
        ZonedDateTime.parse(episode.created)
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
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Name", fontSize = 18.sp,color = Color(
                Random.nextFloat(),
                Random.nextFloat(),
                Random.nextFloat(),
                1f
            ))
            Text(text = episode.name, fontSize = 18.sp,color = Color(
                Random.nextFloat(),
                Random.nextFloat(),
                Random.nextFloat(),
                1f
            ))
        }
        Spacer(modifier = Modifier.height(12.dp))
        Divider()
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Episode", fontSize = 18.sp,color = Color(
                Random.nextFloat(),
                Random.nextFloat(),
                Random.nextFloat(),
                1f
            ))
            Text(text = episode.episode, fontSize = 18.sp, fontWeight = FontWeight.Bold,color = Color(
                Random.nextFloat(),
                Random.nextFloat(),
                Random.nextFloat(),
                1f
            ))
        }
        Spacer(modifier = Modifier.height(12.dp))
        Divider()
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Air Data", fontSize = 18.sp,color = Color(
                Random.nextFloat(),
                Random.nextFloat(),
                Random.nextFloat(),
                1f
            ))
            Text(text = episode.airDate, fontSize = 18.sp, fontWeight = FontWeight.Bold,color = Color(
                Random.nextFloat(),
                Random.nextFloat(),
                Random.nextFloat(),
                1f
            ))
        }
        Spacer(modifier = Modifier.height(12.dp))
        Divider()
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Created", fontSize = 18.sp,color = Color(
                Random.nextFloat(),
                Random.nextFloat(),
                Random.nextFloat(),
                1f
            ))
            Text(
                text = time?.dayOfMonth.toString() + "-" + time?.monthValue.toString() + "-" + time?.year,
                fontSize = 18.sp, fontWeight = FontWeight.Bold,
                color = Color(
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
        Text(text = "Characters", fontSize = 18.sp, modifier = Modifier.padding(start = 12.dp),color = Color(
            Random.nextFloat(),
            Random.nextFloat(),
            Random.nextFloat(),
            1f
        ))
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow {
            items(episode.characters) { character ->
                val createdTime = try {
                    ZonedDateTime.parse(character.created)
                } catch (e: Exception) {
                    null
                }
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(150.dp)
                        .clickable {
                            openCharacter.invoke(character.id)
                        },
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Column {
                        AsyncImage(
                            model = character.image, contentDescription = "Character image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = character.name, fontSize = 16.sp, fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color(
                                Random.nextFloat(),
                                Random.nextFloat(),
                                Random.nextFloat(),
                                1f
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = character.status,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color(
                                Random.nextFloat(),
                                Random.nextFloat(),
                                Random.nextFloat(),
                                1f
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = character.type,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color(
                                Random.nextFloat(),
                                Random.nextFloat(),
                                Random.nextFloat(),
                                1f
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = createdTime?.dayOfMonth.toString() + "-" + createdTime?.monthValue.toString() + "-" + createdTime?.year,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color(
                                Random.nextFloat(),
                                Random.nextFloat(),
                                Random.nextFloat(),
                                1f
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}