package uz.graphql.ricky_and_morty_presenter.screens.locations

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
import uz.graphql.ricky_and_morty_domen.model.location.LocationListWithDetailsData
import uz.graphql.ricky_and_morty_presenter.navigation.ScreenRoutes
import uz.graphql.ricky_and_morty_presenter.screens.locations.views.ItemViewLocationDetails
import uz.graphql.ricky_and_morty_presenter.ui.theme.White
import uz.graphql.ricky_and_morty_presenter.utils.DefaultScreenError
import uz.graphql.ricky_and_morty_presenter.utils.DefaultScreenLoading
import uz.graphql.ricky_and_morty_presenter.vieewModels.locations.ViewModelLocationsListWithDetails

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 5:06 PM for Ricky And Morty.
 */
@Composable
fun ScreenLocationsListWithDetails(
    navController: NavHostController = rememberNavController(),
    viewModel: ViewModelLocationsListWithDetails = hiltViewModel()
) {

    val state = viewModel.state.value
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is ViewModelLocationsListWithDetails.EventViewModelLocationsListWithDetails.OpenResidents -> {
                    navController.navigate(route = ScreenRoutes.Character.CharacterDetailsScreen(event.id).route)
                }
                is ViewModelLocationsListWithDetails.EventViewModelLocationsListWithDetails.OpenLocation -> {
                    navController.navigate(route = ScreenRoutes.Location.LocationDetailsScreen(event.id).route)
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
                    viewModel.onEvent(ViewModelLocationsListWithDetails.EventUILocationsListWithDetails.Refresh)
                }
            } else if (state.locations.isNotEmpty()) {
                LocationsListDetailsScreen(
                    list = state.locations,
                    openResident = { id ->
                        viewModel.onEvent(ViewModelLocationsListWithDetails.EventUILocationsListWithDetails.OpenResidents(id))
                    },
                    openLocation = { id ->
                        viewModel.onEvent(ViewModelLocationsListWithDetails.EventUILocationsListWithDetails.OpenLocation(id))
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
private fun LocationsListDetailsScreen(
    modifier: Modifier = Modifier,
    list: List<LocationListWithDetailsData>,
    openResident: (String) -> Unit,
    openLocation: (String) -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(list) { item ->
            ItemViewLocationDetails(
                item = item,
                openResident = openResident,
                openLocation = openLocation
            )
        }
    }
}