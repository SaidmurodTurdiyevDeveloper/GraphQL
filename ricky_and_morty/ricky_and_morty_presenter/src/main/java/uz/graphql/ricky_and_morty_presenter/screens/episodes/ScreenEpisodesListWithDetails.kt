package uz.graphql.ricky_and_morty_presenter.screens.episodes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.collectLatest
import uz.graphql.ricky_and_morty_domen.model.episode.EpisodeListWithDetailsData
import uz.graphql.ricky_and_morty_presenter.navigation.ScreenRoutes
import uz.graphql.ricky_and_morty_presenter.screens.episodes.views.ItemViewEpisodeDetails
import uz.graphql.ricky_and_morty_presenter.ui.theme.White
import uz.graphql.ricky_and_morty_presenter.utils.DefaultScreenError
import uz.graphql.ricky_and_morty_presenter.utils.DefaultScreenLoading
import uz.graphql.ricky_and_morty_presenter.vieewModels.episodes.ViewModelEpisodesListWithDetails

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 5:05 PM for Ricky And Morty.
 */
@Composable
fun ScreenEpisodesListWithDetails(
    navController: NavHostController = rememberNavController(),
    viewModel: ViewModelEpisodesListWithDetails = hiltViewModel()
) {

    val state = viewModel.state.value
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is ViewModelEpisodesListWithDetails.EventViewModelEpisodesListWithDetails.OpenCharacterDetailsScreen ->{
                    navController.navigate(route = ScreenRoutes.Character.CharacterDetailsScreen(event.id).route)
                }
                is ViewModelEpisodesListWithDetails.EventViewModelEpisodesListWithDetails.OpenEpisodeDetailsScreen -> {
                    navController.navigate(route = ScreenRoutes.Episode.EpisodeDetailsScreen(event.id).route)
                }
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Selected episodes")
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
                    viewModel.onEvent(ViewModelEpisodesListWithDetails.EventUIEpisodesListWithDetails.Refresh)
                }
            } else if (state.episodes.isNotEmpty()) {
                EpisodesListDetailsScreen(
                    list = state.episodes,
                    openEpisode = { id ->
                        viewModel.onEvent(ViewModelEpisodesListWithDetails.EventUIEpisodesListWithDetails.OpenEpisodeDetailsScreen(id))
                    },
                    openCharacter = { id ->
                        viewModel.onEvent(ViewModelEpisodesListWithDetails.EventUIEpisodesListWithDetails.OpenCharacterDetailsScreen(id))
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
private fun EpisodesListDetailsScreen(
    modifier: Modifier = Modifier,
    list: List<EpisodeListWithDetailsData>,
    openEpisode: (String) -> Unit,
    openCharacter: (String) -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(list) { item ->
            ItemViewEpisodeDetails(
                item = item,
                openCharacter = openCharacter,
                openEpisode = openEpisode
            )
        }
    }
}