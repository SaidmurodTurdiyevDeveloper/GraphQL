package uz.graphql.ricky_and_morty_presenter.screens.characters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uz.graphql.ricky_and_morty_presenter.navigation.RickyAndMortyScreens

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 3:36 PM for Ricky And Morty.
 */
@Composable
fun CharacterDetailsScreen(
    navController: NavHostController = rememberNavController()
) {
    Column {
        Text(text = "Episode", modifier = Modifier.clickable {
            navController.navigate(route = RickyAndMortyScreens.Episode.EpisodeDetailsScreen.route)
        })
        Text(text = "Location", modifier = Modifier.clickable {
            navController.navigate(route = RickyAndMortyScreens.Location.LocationDetailsScreen.route)
        })
    }
}