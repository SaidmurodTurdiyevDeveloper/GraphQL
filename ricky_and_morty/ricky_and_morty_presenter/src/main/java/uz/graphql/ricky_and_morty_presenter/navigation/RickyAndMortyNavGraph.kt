package uz.graphql.ricky_and_morty_presenter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uz.graphql.ricky_and_morty_presenter.screens.characters.CharacterDetailsScreen
import uz.graphql.ricky_and_morty_presenter.screens.characters.CharactersListScreen
import uz.graphql.ricky_and_morty_presenter.screens.characters.CharactersListWithDetailsScreen
import uz.graphql.ricky_and_morty_presenter.screens.episodes.EpisodesListWithDetailsScreen
import uz.graphql.ricky_and_morty_presenter.screens.episodes.EpisodeDetailsScreen
import uz.graphql.ricky_and_morty_presenter.screens.episodes.EpisodesListScreen
import uz.graphql.ricky_and_morty_presenter.screens.locations.LocationsListWithDetailsScreen
import uz.graphql.ricky_and_morty_presenter.screens.locations.LocationDetailsScreen
import uz.graphql.ricky_and_morty_presenter.screens.locations.LocationsListScreen

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/23/2023 1:03 AM for Ricky And Morty.
 */
@Composable
fun RickyAndMortyNavGraph(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = RickyAndMortyScreens.CharactersScreen.route,
        modifier = modifier
    ) {
        composable(route = RickyAndMortyScreens.CharactersScreen.route) {
            CharactersListScreen(navController = navController)
        }
        composable(route = RickyAndMortyScreens.EpisodesScreen.route) {
            EpisodesListScreen(navController = navController)
        }
        composable(route = RickyAndMortyScreens.LocationsScreen.route) {
            LocationsListScreen(navController = navController)
        }
        composable(route = RickyAndMortyScreens.Character.CharacterDetailsScreen.route) {
            CharacterDetailsScreen(navController = navController)
        }
        composable(route = RickyAndMortyScreens.Character.CharactersListDetailsScreen.route) {
            CharactersListWithDetailsScreen(navController = navController)
        }
        composable(route = RickyAndMortyScreens.Episode.EpisodeDetailsScreen.route) {
            EpisodeDetailsScreen(navController = navController)
        }
        composable(route = RickyAndMortyScreens.Episode.EpisodesListDetailsScreen.route) {
            EpisodesListWithDetailsScreen(navController = navController)
        }
        composable(route = RickyAndMortyScreens.Location.LocationDetailsScreen.route) {
            LocationDetailsScreen(navController = navController)
        }
        composable(route = RickyAndMortyScreens.Location.LocationsListDetailsScreen.route) {
            LocationsListWithDetailsScreen(navController = navController)
        }
    }
}