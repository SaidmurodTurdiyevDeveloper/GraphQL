package uz.graphql.ricky_and_morty_domen.model.episode


import uz.graphql.EpisodeListQuery
import uz.graphql.EpisodeListWithFilterQuery

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
