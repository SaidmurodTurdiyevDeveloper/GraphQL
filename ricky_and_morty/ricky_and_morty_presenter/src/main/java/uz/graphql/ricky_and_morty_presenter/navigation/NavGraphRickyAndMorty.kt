package uz.graphql.ricky_and_morty_presenter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import uz.graphql.ricky_and_morty_presenter.screens.characters.ScreenCharacterDetails
import uz.graphql.ricky_and_morty_presenter.screens.characters.ScreenCharactersList
import uz.graphql.ricky_and_morty_presenter.screens.characters.ScreenCharactersListWithDetails
import uz.graphql.ricky_and_morty_presenter.screens.episodes.ScreenEpisodesListWithDetails
import uz.graphql.ricky_and_morty_presenter.screens.episodes.ScreenEpisodeDetails
import uz.graphql.ricky_and_morty_presenter.screens.episodes.ScreenEpisodesList
import uz.graphql.ricky_and_morty_presenter.screens.locations.ScreenLocationsListWithDetails
import uz.graphql.ricky_and_morty_presenter.screens.locations.ScreenLocationDetails
import uz.graphql.ricky_and_morty_presenter.screens.locations.ScreenLocationsList
import uz.graphql.ricky_and_morty_presenter.utils.MoveIdConstants

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
        startDestination = ScreenRoutes.CharactersScreen.route,
        modifier = modifier
    ) {
        composable(route = ScreenRoutes.CharactersScreen.route) {
            ScreenCharactersList(navController = navController)
        }
        composable(route = ScreenRoutes.EpisodesScreen.route) {
            ScreenEpisodesList(navController = navController)
        }
        composable(route = ScreenRoutes.LocationsScreen.route) {
            ScreenLocationsList(navController = navController)
        }
        composable(
            route = ScreenRoutes.Character.CharacterDetailsScreen(MoveIdConstants.moveCharacterId).route,
            arguments = listOf(
                navArgument(
                    name = MoveIdConstants.moveCharacterId
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                })
        ) {
            ScreenCharacterDetails(navController = navController)
        }
        composable(route = ScreenRoutes.Character.CharactersListDetailsScreen(MoveIdConstants.moveCharactersIdsList).route,
            arguments = listOf(
                navArgument(
                    name = MoveIdConstants.moveCharactersIdsList
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                })) {
            ScreenCharactersListWithDetails(navController = navController)
        }
        composable(route = ScreenRoutes.Episode.EpisodeDetailsScreen(MoveIdConstants.moveEpisodeId).route,
            arguments = listOf(
                navArgument(
                    name = MoveIdConstants.moveEpisodeId
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                })) {
            ScreenEpisodeDetails(navController = navController)
        }
        composable(route = ScreenRoutes.Episode.EpisodesListDetailsScreen(MoveIdConstants.moveEpisodesIdsList).route,
            arguments = listOf(
                navArgument(
                    name = MoveIdConstants.moveEpisodesIdsList
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                })) {
            ScreenEpisodesListWithDetails(navController = navController)
        }
        composable(route = ScreenRoutes.Location.LocationDetailsScreen(MoveIdConstants.moveLocationId).route,
            arguments = listOf(
                navArgument(
                    name = MoveIdConstants.moveLocationId
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                })) {
            ScreenLocationDetails(navController = navController)
        }
        composable(route = ScreenRoutes.Location.LocationsListDetailsScreen(MoveIdConstants.moveLocationsIdsList).route,
            arguments = listOf(
                navArgument(
                    name = MoveIdConstants.moveLocationsIdsList
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                })) {
            ScreenLocationsListWithDetails(navController = navController)
        }
    }
}