package uz.graphql.ricky_and_morty_presenter.screens

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import uz.graphql.ricky_and_morty_presenter.model.BottomNavItem
import uz.graphql.ricky_and_morty_presenter.navigation.RickyAndMortyNavGraph
import uz.graphql.ricky_and_morty_presenter.navigation.RickyAndMortyScreens
import uz.graphql.ricky_and_morty_presenter.ui.theme.Orange

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 3:56 PM for Ricky And Morty.
 */
@Composable
fun RickyAndMortyScreen(
    navController: NavHostController = rememberNavController()
) {
    val bottomAppbarList = listOf(
        BottomNavItem(
            name = "Character",
            route = RickyAndMortyScreens.CharactersScreen.route,
            icon = Icons.Filled.Person,
            badgeCount = 0
        ),
        BottomNavItem(
            name = "Episode",
            route = RickyAndMortyScreens.EpisodesScreen.route,
            icon = ImageVector.vectorResource(id = uz.graphql.ricky_and_morty_presenter.R.drawable.play_episode),
            badgeCount = 0
        ),
        BottomNavItem(
            name = "Location",
            route = RickyAndMortyScreens.LocationsScreen.route,
            icon = Icons.Filled.LocationOn,
            badgeCount = 0
        )
    )
    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val showBottom = bottomAppbarList.find { item ->
                item.route == currentDestination?.route
            }
            if (showBottom != null) {
                BottomNavigationBar(items = bottomAppbarList,
                    navController = navController,
                    onItemClick = {
                        navController.navigate(it.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    })
            }
        }
    ) { innerPadding ->
        RickyAndMortyNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {

    val selectedColor = if (isSystemInDarkTheme()) Orange else Color.Green
    val unSelectedColor = if (isSystemInDarkTheme()) Color.LightGray else Color.Gray
    BottomNavigation(
        modifier = modifier,
        backgroundColor = if (isSystemInDarkTheme()) Color.DarkGray else Color.White,
        elevation = 5.dp,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { item ->
            val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
            BottomNavigationItem(
                selected = selected,
                selectedContentColor = selectedColor,
                unselectedContentColor = unSelectedColor,
                onClick = { onItemClick(item) },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (item.badgeCount > 0) {
                            BadgedBox(badge = {
                                Text(
                                    text = item.badgeCount.toString(),
                                    style = TextStyle(
                                        color = if (selected)
                                            selectedColor
                                        else
                                            unSelectedColor,
                                        fontSize = 9.sp
                                    )
                                )
                            }) {
                                Icon(imageVector = item.icon, contentDescription = "Image bottom navigation")
                            }
                        } else {
                            Icon(imageVector = item.icon, contentDescription = "Image bottom navigation")
                        }
                        if (selected) {
                            Text(
                                text = item.name, style = TextStyle(
                                    color = if (selected)
                                        selectedColor
                                    else
                                        unSelectedColor,
                                    fontSize = 11.sp
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                })
        }
    }
}