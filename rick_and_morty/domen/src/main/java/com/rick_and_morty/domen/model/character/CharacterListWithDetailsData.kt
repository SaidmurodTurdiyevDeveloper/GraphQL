package com.rick_and_morty.domen.model.character

import com.rick_and_morty.data_graphql.CharactersWithIdsQuery

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 11:47 AM for Rick And Morty GraphQL.
 */
data class CharacterListWithDetailsData(
    val image: String,
    val name: String,
    val status: String,
    val created: String,
    val episode: List<CharactersWithDetailsEpisodesData>,
    val gender: String,
    val location: CharacterWithDetailsLocation,
    val origin: CharacterWithDetailsLocation,
    val species: String,
    val type: String
)

fun CharactersWithIdsQuery.CharactersById.toCharacter(): CharacterListWithDetailsData {
    val newEpisode = episode.map { data ->
        CharactersWithDetailsEpisodesData(
            name = data?.name ?: "",
            episode = data?.episode ?: ""
        )
    }
    return CharacterListWithDetailsData(
        image = image ?: "",
        name = name ?: "",
        status = status ?: "",
        created = created ?: "",
        episode = newEpisode,
        gender = gender ?: "",
        location = CharacterWithDetailsLocation(
            id = location?.id ?: "",
            name = location?.name ?: "",
            type = location?.type ?: ""
        ),
        origin = CharacterWithDetailsLocation(
            id = origin?.id ?: "",
            name = origin?.name ?: "",
            type = origin?.type ?: ""
        ),
        species = species ?: "",
        type = type ?: ""
    )
}

private fun createEmptyCharacter(): CharacterListWithDetailsData = CharacterListWithDetailsData(
    image = "",
    name = "",
    status = "",
    created = "",
    episode = emptyList(),
    gender = "",
    location = CharacterWithDetailsLocation(
        id = "",
        name = "",
        type = ""
    ),
    origin = CharacterWithDetailsLocation(
        id = "",
        name = "",
        type = ""
    ),
    species = "",
    type = ""
)

fun List<CharactersWithIdsQuery.CharactersById?>.toCharactersList(): List<CharacterListWithDetailsData> {
    return map { data ->
        data?.toCharacter() ?: createEmptyCharacter()
    }
}

data class CharactersWithDetailsEpisodesData(
    val name: String,
    val episode: String
)

data class CharacterWithDetailsLocation(
    val id: String,
    val name: String,
    val type: String
)
