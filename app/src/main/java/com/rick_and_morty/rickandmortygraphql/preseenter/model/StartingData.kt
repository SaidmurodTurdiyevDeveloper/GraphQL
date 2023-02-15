package com.rick_and_morty.rickandmortygraphql.preseenter.model

import androidx.annotation.DrawableRes

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/13/2023 12:55 PM for Rick And Morty GraphQL.
 */
data class StartingData(
    val image: String?,
    @DrawableRes
    val imageRes: Int,
    val title: String,
    val description: String
)

val pagerList: List<StartingData> = listOf(
    StartingData(
        image = null,
        imageRes = 0,
        title = "",
        description = ""
    ),
    StartingData(
        image = null,
        imageRes = 0,
        title = "",
        description = ""
    ),
    StartingData(
        image = null,
        imageRes = 0,
        title = "",
        description = ""
    )
)
