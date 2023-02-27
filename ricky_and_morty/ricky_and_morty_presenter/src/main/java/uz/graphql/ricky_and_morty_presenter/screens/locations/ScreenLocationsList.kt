package uz.graphql.ricky_and_morty_presenter.screens.locations

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.graphql.ricky_and_morty_domen.model.location.LocationsListData
import uz.graphql.ricky_and_morty_presenter.navigation.ScreenRoutes
import uz.graphql.ricky_and_morty_presenter.screens.locations.views.ItemViewLocation
import uz.graphql.ricky_and_morty_presenter.ui.theme.Blue
import uz.graphql.ricky_and_morty_presenter.ui.theme.ButtonColor
import uz.graphql.ricky_and_morty_presenter.ui.theme.Orange
import uz.graphql.ricky_and_morty_presenter.ui.theme.White
import uz.graphql.ricky_and_morty_presenter.utils.DefaultScreenError
import uz.graphql.ricky_and_morty_presenter.utils.DefaultScreenLoading
import uz.graphql.ricky_and_morty_presenter.vieewModels.locations.ViewModelLocationsList

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 3:43 PM for Ricky And Morty.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScreenLocationsList(
    navController: NavHostController = rememberNavController(),
    viewModel:ViewModelLocationsList= hiltViewModel()
) {

    val state = viewModel.state.value


    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val skipHalfExpanded by remember {
        mutableStateOf(false)
    }
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = skipHalfExpanded
    )
    val scope = rememberCoroutineScope()

    var doubleClickAndAlertDialogLocation: LocationsListData? = null

    var openAlertDialog by remember {
        mutableStateOf(false)
    }


    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is ViewModelLocationsList.EventViewModelLocationsList.OpenLocationDetailsScreen -> {
                    navController.navigate(route = ScreenRoutes.Location.LocationDetailsScreen(event.id).route)
                }
                is ViewModelLocationsList.EventViewModelLocationsList.OpenLocationsListWithDetailsScreen -> {
                    navController.navigate(route = ScreenRoutes.Location.LocationsListDetailsScreen(event.idListGson).route)
                }
                is ViewModelLocationsList.EventViewModelLocationsList.ShowSnackBar -> {

                }
                is ViewModelLocationsList.EventViewModelLocationsList.ShowToast -> {

                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Locations")
                },
                backgroundColor = if (isSystemInDarkTheme()) Color.DarkGray else White,
                actions = {
                    if (state.selectCount == 0)
                        IconButton(onClick = {
                            viewModel.onEvent(ViewModelLocationsList.EventUILocationsList.LoadList)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refresh"
                            )
                        }
                    else {
                        Text(text = state.selectCount.toString())
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    if (state.selectCount == 0)
                        IconButton(onClick = {
                            scope.launch {
                                bottomSheetState.show()
                            }
                        }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = uz.graphql.ricky_and_morty_presenter.R.drawable.filter_list),
                                contentDescription = "Filter"
                            )
                        }
                    else
                        IconButton(onClick = {
                            viewModel.onEvent(ViewModelLocationsList.EventUILocationsList.ClearSelectedItem)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close"
                            )
                        }
                }
            )
        },
        floatingActionButton = {
            if (state.selectCount != 0)
                FloatingActionButton(onClick = {
                    viewModel.onEvent(ViewModelLocationsList.EventUILocationsList.OpenLocationsListWithDetailsScreen)
                }) {
                    Icon(imageVector = Icons.Default.List, contentDescription = "Open Selectionlist")
                }
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
                DefaultScreenError(
                    modifier = Modifier.fillMaxSize(),
                    errorMessage = state.error
                ) {
                    viewModel.onEvent(ViewModelLocationsList.EventUILocationsList.LoadList)
                }
            } else {
                if (openAlertDialog) {
                    AlertDialog(
                        onDismissRequest = {
                            doubleClickAndAlertDialogLocation = null
                            openAlertDialog = false
                        },
                        title = {
                            Text(text = doubleClickAndAlertDialogLocation?.name ?: "Character")
                        },
                        text = {
                            Text(
                                text =
                                "Id - " + (doubleClickAndAlertDialogLocation?.id ?: "--") + "\n" +
                                        "Dimension - " + (doubleClickAndAlertDialogLocation?.dimension ?: "..") + "\n" +
                                        "Type - " + (doubleClickAndAlertDialogLocation?.type ?: "..") + "\n" +
                                        "Created - " + (doubleClickAndAlertDialogLocation?.created ?: "..")
                            )

                        }, confirmButton = {
                            TextButton(onClick = {
                                if (doubleClickAndAlertDialogLocation != null)
                                    viewModel.onEvent(ViewModelLocationsList.EventUILocationsList.OpenLocationDetailsScreen(id = doubleClickAndAlertDialogLocation?.id ?: ""))
                                doubleClickAndAlertDialogLocation = null
                                openAlertDialog = false
                            }) {
                                Text(text = "Open")
                            }
                        }, dismissButton = {
                            TextButton(onClick = {
                                doubleClickAndAlertDialogLocation = null
                                openAlertDialog = false
                            }) {
                                Text(text = "Close")
                            }
                        })
                }
                ModalBottomSheetLayout(
                    sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    sheetState = bottomSheetState,
                    sheetContent = {
                        FilterLocationScreen(viewModel = viewModel, scope = scope, bottomSheetState = bottomSheetState)
                    }) {
                    if (state.locations.isNotEmpty()) {
                        LocationsListScreen(
                            list = state.locations,
                            isLoading = state.isLoadingItem,
                            openCharacter = { id ->
                                viewModel.onEvent(ViewModelLocationsList.EventUILocationsList.OpenLocationDetailsScreen(id))
                            },
                            selectItem = { character ->
                                viewModel.onEvent(ViewModelLocationsList.EventUILocationsList.SelectItem(character))
                            },
                            openDialog = { data ->
                                doubleClickAndAlertDialogLocation = data
                                openAlertDialog = true
                            },
                            loadNextPage = {
                                viewModel.onEvent(ViewModelLocationsList.EventUILocationsList.LoadNextPage)
                            },
                            selectCount = state.selectCount
                        )
                    } else {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "List is empty")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun FilterLocationScreen(
    viewModel: ViewModelLocationsList,
    scope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var dimension by remember { mutableStateOf(TextFieldValue("")) }
    var type by remember { mutableStateOf(TextFieldValue("")) }
    LazyColumn(modifier = Modifier.padding(24.dp)) {
        item {
            Text(text = "Filter", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
        }
        item {
            OutlinedTextField(
                value = name,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { newText ->
                    name = newText
                },
                label = {
                    Text(text = "Name")
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
        item {
            OutlinedTextField(
                value = dimension,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { newText ->
                    dimension = newText
                },
                label = {
                    Text(text = "Dimension")
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
        item {
            OutlinedTextField(
                value = type,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { newText ->
                    type = newText
                },
                label = {
                    Text(text = "Type")
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .background(if (isSystemInDarkTheme()) Orange else Blue, shape = RoundedCornerShape(8.dp))
                ) {
                    TextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            scope.launch {
                                bottomSheetState.hide()
                            }
                            viewModel.onEvent(
                                ViewModelLocationsList.EventUILocationsList.Filter(
                                    name = name.text.trim(),
                                    dimension = dimension.text.trim(),
                                    type = type.text.trim()
                                )
                            )
                        }
                    ) {
                        Text(text = "Filter", fontSize = 18.sp, color = if (isSystemInDarkTheme()) Color.Black else White, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun LocationsListScreen(
    list: List<LocationsListData>,
    isLoading: Boolean = false,
    openCharacter: (String) -> Unit,
    selectItem: (LocationsListData) -> Unit,
    openDialog: (LocationsListData) -> Unit,
    loadNextPage: () -> Unit,
    selectCount: Int
) {
    LazyColumn {
        items(list, key = { item -> item.id }) { data ->
            if (data == list.last()) {
                loadNextPage.invoke()
            }
            ItemViewLocation(
                item = data,
                onClick = { id ->
                    if (selectCount != 0) {
                        selectItem.invoke(data)
                    } else openCharacter.invoke(id)
                },
                onLongClick = {
                    selectItem.invoke(data)
                },
                onDoubleClick = {
                    openDialog.invoke(data)
                }
            )
        }
        if (isLoading) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}