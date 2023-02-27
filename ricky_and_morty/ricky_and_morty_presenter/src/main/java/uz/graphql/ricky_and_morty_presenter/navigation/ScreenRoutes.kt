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
        data class CharactersListDetailsScreen(val idsListGson: String,val inputNavHost:Boolean=false) : Character("characters_list_details_screen?${MoveIdConstants.moveCharactersIdsList}="+if (inputNavHost)"{$idsListGson}" else idsListGson)
        data class CharacterDetailsScreen(val id: String,val inputNavHost:Boolean=false) : Character("character_details_screen?${MoveIdConstants.moveCharacterId}="+if (inputNavHost)"{$id}" else id)
    }

    sealed class Episode(val route: String) {
        data class EpisodesListDetailsScreen(val idsListGson: String,val inputNavHost:Boolean=false) : Episode("episodes_list_details_screen?${MoveIdConstants.moveEpisodesIdsList}="+if (inputNavHost)"{$idsListGson}" else idsListGson)
        data class EpisodeDetailsScreen(val id: String,val inputNavHost:Boolean=false) : Episode("episode_details_screen?${MoveIdConstants.moveEpisodeId}="+if (inputNavHost)"{$id}" else id)
    }

    sealed class Location(val route: String) {
        data class LocationsListDetailsScreen(val idsListGson: String,val inputNavHost:Boolean=false) : ScreenRoutes("locations_list_details_screen?${MoveIdConstants.moveLocationsIdsList}="+if (inputNavHost)"{$idsListGson}" else idsListGson)
        data class LocationDetailsScreen(val id: String,val inputNavHost:Boolean=false) : ScreenRoutes("location_details_screen?${MoveIdConstants.moveLocationId}="+if (inputNavHost)"{$id}" else id)
    }
}