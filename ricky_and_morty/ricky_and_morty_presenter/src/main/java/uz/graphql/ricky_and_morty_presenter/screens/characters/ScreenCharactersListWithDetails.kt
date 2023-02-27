package uz.graphql.ricky_and_morty_presenter.screens.characters

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
import uz.graphql.ricky_and_morty_domen.model.character.CharacterListWithDetailsData
import uz.graphql.ricky_and_morty_presenter.navigation.ScreenRoutes
import uz.graphql.ricky_and_morty_presenter.screens.characters.views.ItemViewCharacterDetails
import uz.graphql.ricky_and_morty_presenter.ui.theme.White
import uz.graphql.ricky_and_morty_presenter.utils.DefaultScreenError
import uz.graphql.ricky_and_morty_presenter.utils.DefaultScreenLoading
import uz.graphql.ricky_and_morty_presenter.vieewModels.characters.ViewModelCharacterDetails
import uz.graphql.ricky_and_morty_presenter.vieewModels.characters.ViewModelCharactersListWithDetails

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 3:35 PM for Ricky And Morty.
 */
@Composable
fun ScreenCharactersListWithDetails(
    navController: NavHostController = rememberNavController(),
    viewModel: ViewModelCharactersListWithDetails = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is ViewModelCharactersListWithDetails.EventViewModelCharactersListWithDetails.OpenCharacterDetailsScreen -> {
                    navController.navigate(route = ScreenRoutes.Character.CharacterDetailsScreen(event.id).route)
                }
                is ViewModelCharactersListWithDetails.EventViewModelCharactersListWithDetails.OpenEpisodeDetailsScreen -> {
                    navController.navigate(route = ScreenRoutes.Episode.EpisodeDetailsScreen(event.id).route)
                }
                is ViewModelCharactersListWithDetails.EventViewModelCharactersListWithDetails.OpenLocationDetailsScreen -> {
                    navController.navigate(route = ScreenRoutes.Location.LocationDetailsScreen(event.id).route)
                }
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Selected characters")
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
                    viewModel.onEvent(ViewModelCharactersListWithDetails.EventUICharactersListWithDetails.Refresh)
                }
            } else if (state.characters.isNotEmpty()) {
                CharactersListWithDetailsScreen(
                    list = state.characters,
                    openEpisode = { id ->
                        viewModel.onEvent(ViewModelCharactersListWithDetails.EventUICharactersListWithDetails.OpenEpisodeDetailsScreen(id))
                    }, openLocation = { id ->
                        viewModel.onEvent(ViewModelCharactersListWithDetails.EventUICharactersListWithDetails.OpenLocationDetailsScreen(id))
                    },
                    openCharacter = { id ->
                        viewModel.onEvent(ViewModelCharactersListWithDetails.EventUICharactersListWithDetails.OpenCharacterDetailsScreen(id))
                    }
                )
            } else {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "We cannot find any Characters",
                        fontSize = 18.sp
                    )
                }
            }
        }
    }

}

@Composable
private fun CharactersListWithDetailsScreen(
    modifier: Modifier = Modifier,
    list: List<CharacterListWithDetailsData>,
    openEpisode: (String) -> Unit,
    openLocation: (String) -> Unit,
    openCharacter: (String) -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(list) { item ->
            ItemViewCharacterDetails(item = item, openCharacter = openCharacter, openEpisode = openEpisode, openLocation = openLocation)
        }
    }
}