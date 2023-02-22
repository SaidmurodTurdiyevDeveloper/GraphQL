package uz.graphql.ricky_and_morty_presenter.model

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 11:56 PM for Ricky And Morty.
 */
data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector,
    val badgeCount:Int
)
