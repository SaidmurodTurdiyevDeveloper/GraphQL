package uz.graphql.ricky_and_morty_domen.model.episode

import uz.graphql.EpisodesWithIdsQuery

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 3:16 PM for Rick And Morty GraphQL.
 */
data class EpisodeListWithDetailsData(
    val id: String,
    val episode: String,
    val name: String,
    val airDate: String,
    val created: String,
    val characters: List<EpisodesWithDetailsCharactersData>
)

data class EpisodesWithDetailsCharactersData(
    val id: String,
    val name: String
)

fun EpisodesWithIdsQuery.EpisodesById.toEpisode(): EpisodeListWithDetailsData {
    val newCharacterList = characters.map { data ->
        EpisodesWithDetailsCharactersData(
            id = data?.id ?: "",
            name = data?.name?.ifBlank { "-" } ?: ""
        )
    }

    return EpisodeListWithDetailsData(
        id=id?:"",
        episode = episode?.ifBlank { "-" } ?: "",
        name = name?.ifBlank { "-" } ?: "",
        airDate = air_date?.ifBlank { "-" } ?: "",
        created = created ?: "",
        characters = newCharacterList
    )
}

fun List<EpisodesWithIdsQuery.EpisodesById?>.toEpisodesList(): List<EpisodeListWithDetailsData> {
    return map { data ->
        data?.toEpisode() ?: createEmptyEpisode()
    }
}

private fun createEmptyEpisode(): EpisodeListWithDetailsData {
    return EpisodeListWithDetailsData(
        id="",
        episode = "",
        name = "",
        airDate = "",
        created = "",
        characters = emptyList()
    )
}
