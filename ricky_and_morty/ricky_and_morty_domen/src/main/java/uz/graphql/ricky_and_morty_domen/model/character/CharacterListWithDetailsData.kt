package uz.graphql.ricky_and_morty_domen.model.character

import uz.graphql.CharactersWithIdsQuery

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 11:47 AM for Rick And Morty GraphQL.
 */
data class CharacterListWithDetailsData(
    val id: String,
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
            id = data?.id ?: "",
            name = data?.name ?: "",
            episode = data?.episode ?: ""
        )
    }
    return CharacterListWithDetailsData(
        id=id?:"",
        image = image ?: "",
        name = name?.ifBlank { "-" } ?: "",
        status = status?.ifBlank { "-" } ?: "",
        created = created ?: "",
        episode = newEpisode,
        gender = gender?.ifBlank { "-" } ?: "",
        location = CharacterWithDetailsLocation(
            id = location?.id ?: "",
            name = location?.name?.ifBlank { "-" } ?: "",
            type = location?.type?.ifBlank { "-" } ?: ""
        ),
        origin = CharacterWithDetailsLocation(
            id = origin?.id ?: "",
            name = origin?.name?.ifBlank { "-" } ?: "",
            type = origin?.type?.ifBlank { "-" } ?: ""
        ),
        species = species?.ifBlank { "-" } ?: "",
        type = type?.ifBlank { "-" } ?: ""
    )
}

private fun createEmptyCharacter(): CharacterListWithDetailsData = CharacterListWithDetailsData(
    id = "",
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
    val id: String,
    val name: String,
    val episode: String
)

data class CharacterWithDetailsLocation(
    val id: String,
    val name: String,
    val type: String
)
