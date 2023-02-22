package uz.graphql.ricky_and_morty_domen.model.character

import uz.graphql.CharacterQuery

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 11:47 AM for Rick And Morty GraphQL.
 */
data class CharacterData(
    val image: String,
    val name: String,
    val status: String,
    val created: String,
    val episode: List<CharacterEpisodesData>,
    val gender: String,
    val location: CharacterLocation,
    val origin: CharacterLocation,
    val species: String,
    val type: String
)

fun CharacterQuery.Character.toCharacter(): CharacterData {
    val newEpisode = episode.map { data ->
        CharacterEpisodesData(
            id = data?.id ?: "",
            name = data?.name ?: "",
            episode = data?.episode ?: "",
            created = data?.created ?: "",
            airDate = data?.air_date ?: ""
        )
    }
    return CharacterData(
        image = image ?: "",
        name = name ?: "",
        status = status ?: "",
        created = created ?: "",
        episode = newEpisode,
        gender = gender ?: "",
        location = CharacterLocation(
            id = location?.id ?: "",
            name = location?.name ?: "",
            type = location?.type ?: ""
        ),
        origin = CharacterLocation(
            id = origin?.id ?: "",
            name = origin?.name ?: "",
            type = origin?.type ?: ""
        ),
        species = species ?: "",
        type = type ?: ""
    )
}

data class CharacterEpisodesData(
    val id: String,
    val name: String,
    val episode: String,
    val created: String,
    val airDate: String
)

data class CharacterLocation(
    val id: String,
    val name: String,
    val type: String
)
