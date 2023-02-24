package uz.graphql.ricky_and_morty_presenter.screens.characters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uz.graphql.ricky_and_morty_domen.model.character.CharactersListData
import uz.graphql.ricky_and_morty_presenter.navigation.ScreenRoutes
import uz.graphql.ricky_and_morty_presenter.utils.DefaultScreenError
import uz.graphql.ricky_and_morty_presenter.utils.DefaultScreenLoading
import uz.graphql.ricky_and_morty_presenter.vieewModels.characters.ViewModelCharacterDetails
import uz.graphql.ricky_and_morty_presenter.vieewModels.characters.ViewModelCharactersList

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 3:35 PM for Ricky And Morty.
 */
@Composable
fun ScreenCharactersList(
    navController: NavHostController = rememberNavController(),
    viewModel: ViewModelCharactersList = hiltViewModel()
) {
//    Column {
//        Text(text = "Character", modifier = Modifier.clickable {
//            navController.navigate(route = ScreenRoutes.Character.CharacterDetailsScreen().route)
//        })
//        Text(text = "Characters", modifier = Modifier.clickable {
//            navController.navigate(route = ScreenRoutes.Character.CharactersListDetailsScreen.route)
//        })
//    }
    val state = viewModel.state.value
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Character")
                },
                actions = {
                    IconButton(onClick = {
                        // FilterDialog
                    }) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = "Refresh")
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
                    viewModel.onEvent(ViewModelCharactersList.EventUICharactersList.LoadList)
                }
            } else if (state.characters.isNotEmpty()) {

            }
        }

    }
}

@Composable
private fun CharacterListScreen(
    list: List<CharactersListData>,
    openCharacter: (String) -> Unit,

    ) {

}