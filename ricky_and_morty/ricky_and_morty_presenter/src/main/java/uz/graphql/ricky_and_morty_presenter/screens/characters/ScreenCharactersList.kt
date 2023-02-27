package uz.graphql.ricky_and_morty_presenter.screens.characters

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
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
import uz.graphql.ricky_and_morty_domen.model.character.CharactersListData
import uz.graphql.ricky_and_morty_presenter.navigation.ScreenRoutes
import uz.graphql.ricky_and_morty_presenter.screens.characters.views.ItemViewCharacter
import uz.graphql.ricky_and_morty_presenter.ui.RadiGroupButton
import uz.graphql.ricky_and_morty_presenter.ui.theme.Blue
import uz.graphql.ricky_and_morty_presenter.ui.theme.ButtonColor
import uz.graphql.ricky_and_morty_presenter.ui.theme.Orange
import uz.graphql.ricky_and_morty_presenter.ui.theme.White
import uz.graphql.ricky_and_morty_presenter.utils.DefaultScreenError
import uz.graphql.ricky_and_morty_presenter.utils.DefaultScreenLoading
import uz.graphql.ricky_and_morty_presenter.vieewModels.characters.ViewModelCharactersList

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 3:35 PM for Ricky And Morty.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScreenCharactersList(
    navController: NavHostController = rememberNavController(),
    viewModel: ViewModelCharactersList = hiltViewModel()
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

    var doubleClickAndAlertDialogCharacter: CharactersListData? = null

    var openAlertDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is ViewModelCharactersList.EventViewModelCharactersList.OpenCharacterDetailsScreen -> {
                    navController.navigate(route = ScreenRoutes.Character.CharacterDetailsScreen(event.id).route)
                }
                is ViewModelCharactersList.EventViewModelCharactersList.OpenCharactersListWithDetailsScreen -> {
                    navController.navigate(route = ScreenRoutes.Character.CharactersListDetailsScreen(event.idsListGson).route)
                }
                is ViewModelCharactersList.EventViewModelCharactersList.ShowSnackBar -> {

                }
                is ViewModelCharactersList.EventViewModelCharactersList.ShowToast -> {

                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Characters")
                },
                backgroundColor = if (isSystemInDarkTheme()) Color.DarkGray else White,
                actions = {
                    if (state.selectCount == 0)
                        IconButton(onClick = {
                            viewModel.onEvent(ViewModelCharactersList.EventUICharactersList.LoadList)
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
                            viewModel.onEvent(ViewModelCharactersList.EventUICharactersList.ClearSelectedItem)
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
                    viewModel.onEvent(ViewModelCharactersList.EventUICharactersList.OpenCharactersListWithDetailsScreen)
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
                    viewModel.onEvent(ViewModelCharactersList.EventUICharactersList.LoadList)
                }
            } else {
                if (openAlertDialog) {
                    AlertDialog(
                        onDismissRequest = {
                            doubleClickAndAlertDialogCharacter = null
                            openAlertDialog = false
                        },
                        title = {
                            Text(text = doubleClickAndAlertDialogCharacter?.name ?: "Character")
                        },
                        text = {
                            Text(
                                text =
                                "Id - " + (doubleClickAndAlertDialogCharacter?.id ?: "--") + "\n" +
                                        "Status - " + (doubleClickAndAlertDialogCharacter?.status ?: "..") + "\n" +
                                        "Gender - " + (doubleClickAndAlertDialogCharacter?.gender ?: "..") + "\n" +
                                        "Image Url - " + (doubleClickAndAlertDialogCharacter?.image ?: "..") + "\n" +
                                        "Species - " + (doubleClickAndAlertDialogCharacter?.species ?: "..") + "\n" +
                                        "Type - " + (doubleClickAndAlertDialogCharacter?.type ?: "..") + "\n" +
                                        "Created - " + (doubleClickAndAlertDialogCharacter?.created ?: "..")
                            )

                        }, confirmButton = {
                            TextButton(onClick = {
                                if (doubleClickAndAlertDialogCharacter != null)
                                    viewModel.onEvent(ViewModelCharactersList.EventUICharactersList.OpenCharacterDetailsScreen(id = doubleClickAndAlertDialogCharacter?.id ?: ""))
                                doubleClickAndAlertDialogCharacter = null
                                openAlertDialog = false
                            }) {
                                Text(text = "Open")
                            }
                        }, dismissButton = {
                            TextButton(onClick = {
                                doubleClickAndAlertDialogCharacter = null
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
                        FilterCharacterScreen(viewModel = viewModel, scope = scope, bottomSheetState = bottomSheetState)
                    }) {
                    if (state.characters.isNotEmpty()) {
                        CharacterListScreen(
                            list = state.characters,
                            isLoading = state.isLoadingItem,
                            openCharacter = { id ->
                                viewModel.onEvent(ViewModelCharactersList.EventUICharactersList.OpenCharacterDetailsScreen(id))
                            },
                            selectItem = { character ->
                                viewModel.onEvent(ViewModelCharactersList.EventUICharactersList.SelectItem(character))
                            },
                            openDialog = { data ->
                                doubleClickAndAlertDialogCharacter = data
                                openAlertDialog = true
                            },
                            loadNextPage = {
                                viewModel.onEvent(ViewModelCharactersList.EventUICharactersList.LoadNextPage)
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
private fun FilterCharacterScreen(
    viewModel: ViewModelCharactersList,
    scope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
) {
    var gender = ""
    var status = ""
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var species by remember { mutableStateOf(TextFieldValue("")) }
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
                value = species,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { newText ->
                    species = newText
                },
                label = {
                    Text(text = "Species")
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp)
            ) {
                RadiGroupButton(
                    title = "Gender",
                    isVertically = true,
                    modifier = Modifier.weight(1f),
                    items = listOf(
                        "Male",
                        "Female",
                        "unknown",
                        ""
                    ), itemClick = {
                        gender = it
                    })
                RadiGroupButton(
                    title = "Status",
                    isVertically = true,
                    modifier = Modifier.weight(1f),
                    items = listOf(
                        "Alive",
                        "Dead",
                        "unknown",
                        ""
                    ), itemClick = {
                        status = it
                    })
            }
            Spacer(modifier = Modifier.height(8.dp))
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
                    imeAction = ImeAction.Done
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
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
                            Log.d("TTTTDD","Click ishladi")

                            scope.launch {
                                bottomSheetState.hide()
                            }
                            viewModel.onEvent(
                                ViewModelCharactersList.EventUICharactersList.Filter(
                                    gender = gender,
                                    name = name.text.trim(),
                                    species = species.text.trim(),
                                    status = status,
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CharacterListScreen(
    list: List<CharactersListData>,
    isLoading: Boolean = false,
    openCharacter: (String) -> Unit,
    selectItem: (CharactersListData) -> Unit,
    openDialog: (CharactersListData) -> Unit,
    loadNextPage: () -> Unit,
    selectCount: Int
) {
    LazyColumn {
        items(list, key = { item -> item.id }) { data ->
            if (data == list.last()) {
                loadNextPage.invoke()
            }
            ItemViewCharacter(
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
                },
                modifier = Modifier.height(170.dp)
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

