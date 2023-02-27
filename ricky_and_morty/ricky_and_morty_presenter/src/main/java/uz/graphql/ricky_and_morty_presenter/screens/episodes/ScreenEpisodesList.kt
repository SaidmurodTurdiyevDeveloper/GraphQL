package uz.graphql.ricky_and_morty_presenter.screens.episodes

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
import uz.graphql.ricky_and_morty_domen.model.episode.EpisodesListData
import uz.graphql.ricky_and_morty_presenter.navigation.ScreenRoutes
import uz.graphql.ricky_and_morty_presenter.screens.episodes.views.ItemViewEpisode
import uz.graphql.ricky_and_morty_presenter.ui.theme.Blue
import uz.graphql.ricky_and_morty_presenter.ui.theme.ButtonColor
import uz.graphql.ricky_and_morty_presenter.ui.theme.Orange
import uz.graphql.ricky_and_morty_presenter.ui.theme.White
import uz.graphql.ricky_and_morty_presenter.utils.DefaultScreenError
import uz.graphql.ricky_and_morty_presenter.utils.DefaultScreenLoading
import uz.graphql.ricky_and_morty_presenter.vieewModels.episodes.ViewModelEpisodesList

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 3:39 PM for Ricky And Morty.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScreenEpisodesList(
    navController: NavHostController = rememberNavController(),
    viewModel: ViewModelEpisodesList = hiltViewModel()
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

    var doubleClickAndAlertDialogEpisode: EpisodesListData? = null

    var openAlertDialog by remember {
        mutableStateOf(false)
    }


    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is ViewModelEpisodesList.EventViewModelEpisodesList.OpenEpisodeDetailsScreen -> {
                    navController.navigate(route = ScreenRoutes.Episode.EpisodeDetailsScreen(event.id).route)
                }
                is ViewModelEpisodesList.EventViewModelEpisodesList.OpenEpisodesListWithDetailsScreen -> {
                    navController.navigate(route = ScreenRoutes.Episode.EpisodesListDetailsScreen(event.idList).route)
                }
                is ViewModelEpisodesList.EventViewModelEpisodesList.ShowSnackBar -> {

                }
                is ViewModelEpisodesList.EventViewModelEpisodesList.ShowToast -> {

                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Episodes")
                },
                backgroundColor = if (isSystemInDarkTheme()) Color.DarkGray else White,
                actions = {
                    if (state.selectCount == 0)
                        IconButton(onClick = {
                            viewModel.onEvent(ViewModelEpisodesList.EventUIEpisodesList.LoadList)
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
                            viewModel.onEvent(ViewModelEpisodesList.EventUIEpisodesList.ClearSelectedItem)
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
                    viewModel.onEvent(ViewModelEpisodesList.EventUIEpisodesList.OpenEpisodesListWithDetailsScreen)
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
                    viewModel.onEvent(ViewModelEpisodesList.EventUIEpisodesList.LoadList)
                }
            } else {
                if (openAlertDialog) {
                    AlertDialog(
                        onDismissRequest = {
                            doubleClickAndAlertDialogEpisode = null
                            openAlertDialog = false
                        },
                        title = {
                            Text(text = doubleClickAndAlertDialogEpisode?.name ?: "Character")
                        },
                        text = {
                            Text(
                                text =
                                "Id - " + (doubleClickAndAlertDialogEpisode?.id ?: "--") + "\n" +
                                        "Episode - " + (doubleClickAndAlertDialogEpisode?.episode ?: "..") + "\n" +
                                        "Created - " + (doubleClickAndAlertDialogEpisode?.created ?: "..")
                            )

                        }, confirmButton = {
                            TextButton(onClick = {
                                if (doubleClickAndAlertDialogEpisode != null)
                                    viewModel.onEvent(ViewModelEpisodesList.EventUIEpisodesList.OpenEpisodeDetailsScreen(id = doubleClickAndAlertDialogEpisode?.id ?: ""))
                                doubleClickAndAlertDialogEpisode = null
                                openAlertDialog = false
                            }) {
                                Text(text = "Open")
                            }
                        }, dismissButton = {
                            TextButton(onClick = {
                                doubleClickAndAlertDialogEpisode = null
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
                        FilterEpisodeScreen(viewModel = viewModel, scope = scope, bottomSheetState = bottomSheetState)
                    }) {
                    if (state.episodes.isNotEmpty()) {
                        EpisodesListScreen(
                            list = state.episodes,
                            isLoading = state.isLoadingItem,
                            openCharacter = { id ->
                                viewModel.onEvent(ViewModelEpisodesList.EventUIEpisodesList.OpenEpisodeDetailsScreen(id))
                            },
                            selectItem = { character ->
                                viewModel.onEvent(ViewModelEpisodesList.EventUIEpisodesList.SelectItem(character))
                            },
                            openDialog = { data ->
                                doubleClickAndAlertDialogEpisode = data
                                openAlertDialog = true
                            },
                            loadNextPage = {
                                viewModel.onEvent(ViewModelEpisodesList.EventUIEpisodesList.LoadNextPage)
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
private fun FilterEpisodeScreen(
    viewModel: ViewModelEpisodesList,
    scope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var episode by remember { mutableStateOf(TextFieldValue("")) }
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
                value = episode,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { newText ->
                    episode = newText
                },
                label = {
                    Text(text = "Episode")
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
                                ViewModelEpisodesList.EventUIEpisodesList.Filter(
                                    name = name.text.trim(),
                                    episode = episode.text.trim()
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
private fun EpisodesListScreen(
    list: List<EpisodesListData>,
    isLoading: Boolean = false,
    openCharacter: (String) -> Unit,
    selectItem: (EpisodesListData) -> Unit,
    openDialog: (EpisodesListData) -> Unit,
    loadNextPage: () -> Unit,
    selectCount: Int
) {
    LazyColumn {
        items(list, key = { item -> item.id }) { data ->
            if (data == list.last()) {
                loadNextPage.invoke()
            }
            ItemViewEpisode(
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