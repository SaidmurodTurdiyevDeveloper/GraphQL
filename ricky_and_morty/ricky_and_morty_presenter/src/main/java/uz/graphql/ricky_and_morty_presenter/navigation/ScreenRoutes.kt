package uz.graphql.ricky_and_morty_presenter.navigation

import uz.graphql.ricky_and_morty_presenter.utils.MoveIdConstants

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 3:49 PM for Ricky And Morty.
 */
sealed class ScreenRoutes(val route: String) {
    object CharactersScreen : ScreenRoutes("characters_screen")
    object EpisodesScreen : ScreenRoutes("episodes_screen")
    object LocationsScreen : ScreenRoutes("locations_screen")
    sealed class Character(val route: String) {
        data class CharactersListDetailsScreen(val idsListGson: String) : Character("characters_list_details-screen?${MoveIdConstants.moveCharactersIdsList}=$idsListGson")
        data class CharacterDetailsScreen(val id: String) : Character("character_details_screen?${MoveIdConstants.moveCharacterId}=$id")
    }

    sealed class Episode(val route: String) {
        data class EpisodesListDetailsScreen(val idsListGson: String) : Episode("episodes_list_details_screen?${MoveIdConstants.moveEpisodesIdsList}=$idsListGson")
        data class EpisodeDetailsScreen(val id: String) : Episode("episode_details_screen?${MoveIdConstants.moveEpisodeId}=$id")
    }

    sealed class Location(val route: String) {
        data class LocationsListDetailsScreen(val idsListGson: String) : ScreenRoutes("locations_list_details_screen?${MoveIdConstants.moveLocationsIdsList}=$idsListGson")
        data class LocationDetailsScreen(val id: String) : ScreenRoutes("location_details_screen?${MoveIdConstants.moveLocationId}=$id")
    }
}