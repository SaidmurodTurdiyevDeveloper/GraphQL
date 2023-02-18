package com.rick_and_morty.domen.model.episode

import com.rick_and_morty.data_graphql.EpisodeListQuery
import com.rick_and_morty.data_graphql.EpisodeListWithFilterQuery

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 3:16 PM for Rick And Morty GraphQL.
 */
data class EpisodesListData(
    val id: String,
    val name: String,
    val episode: String,
    val created: String
)

fun EpisodeListQuery.Episodes.toEpisodesList(): List<EpisodesListData> {
    return this.results?.map { data ->
        EpisodesListData(
            id = data?.id ?: "",
            name = data?.name ?: "",
            episode = data?.episode ?: "",
            created = data?.created ?: ""
        )
    } ?: emptyList()
}

fun EpisodeListWithFilterQuery.Episodes.toEpisodesList(): List<EpisodesListData> {
    return this.results?.map { data ->
        EpisodesListData(
            id = data?.id ?: "",
            name = data?.name ?: "",
            episode = data?.episode ?: "",
            created = data?.created ?: ""
        )
    } ?: emptyList()
}
