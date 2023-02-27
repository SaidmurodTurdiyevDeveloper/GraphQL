package uz.graphql.ricky_and_morty_domen.model.episode

import uz.graphql.EpisodeQuery

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 3:15 PM for Rick And Morty GraphQL.
 */
data class EpisodeDetailsData(
    val episode: String,
    val name: String,
    val airDate: String,
    val created: String,
    val characters: List<EpisodeCharactersData>
)

data class EpisodeCharactersData(
    val id: String,
    val name: String,
    val image: String,
    val created: String,
    val type: String,
    val status: String
)

fun EpisodeQuery.Episode.toEpisode(): EpisodeDetailsData {
    val newCharacters = characters.map { data ->
        EpisodeCharactersData(
            id = data?.id ?: "",
            name = data?.name?.ifBlank { "-" } ?: "",
            image = data?.image ?: "",
            created = data?.created ?: "",
            type = data?.type?.ifBlank { "-" } ?: "",
            status = data?.status?.ifBlank { "-" } ?: ""
        )
    }
    return EpisodeDetailsData(
        episode = episode?.ifBlank { "-" } ?: "",
        name = name?.ifBlank { "-" } ?: "",
        airDate = air_date?.ifBlank { "-" } ?: "",
        created = created ?: "",
        characters = newCharacters
    )
}
