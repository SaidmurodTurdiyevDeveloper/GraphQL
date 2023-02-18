package com.rick_and_morty.domen.model.episode

import com.rick_and_morty.data_graphql.EpisodesWithIdsQuery

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 3:16 PM for Rick And Morty GraphQL.
 */
data class EpisodeListWithDetailsData(
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
            name = data?.name ?: ""
        )
    }
    return EpisodeListWithDetailsData(
        episode = episode ?: "",
        name = name ?: "",
        airDate = air_date ?: "",
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
        episode = "",
        name = "",
        airDate = "",
        created = "",
        characters = emptyList()
    )
}
