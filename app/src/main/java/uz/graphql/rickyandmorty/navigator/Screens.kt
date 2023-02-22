package uz.graphql.rickyandmorty.navigator

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/13/2023 6:00 PM for Rick And Morty GraphQL.
 */
sealed class Screens(val route: String) {
    object SplashScreen : Screens("splash_screen")
    object StartingScreen : Screens("starting_screen")
}