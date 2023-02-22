package uz.graphql.ricky_and_morty_presenter.navigation

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 3:49 PM for Ricky And Morty.
 */
sealed class RickyAndMortyScreens(val route: String) {
    object CharactersScreen : RickyAndMortyScreens("characters_screen")
    object EpisodesScreen : RickyAndMortyScreens("episodes_screen")
    object LocationsScreen : RickyAndMortyScreens("locations_screen")
    sealed class Character(val route: String) {
        object CharactersListDetailsScreen : Character("characters_list_details-screen")
        object CharacterDetailsScreen : Character("character_details_screen")
    }

    sealed class Episode(val route: String) {
        object EpisodesListDetailsScreen : Episode("episodes_list_details_screen")
        object EpisodeDetailsScreen : Episode("episode_details_screen")
    }

    sealed class Location(val route: String) {
        object LocationsListDetailsScreen : RickyAndMortyScreens("locations_list_details_screen")
        object LocationDetailsScreen : RickyAndMortyScreens("location_details_screen")
    }
}