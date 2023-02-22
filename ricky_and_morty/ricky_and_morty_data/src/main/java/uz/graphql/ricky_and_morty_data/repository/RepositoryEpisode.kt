package uz.graphql.ricky_and_morty_data.repository

import uz.graphql.EpisodeListQuery
import uz.graphql.EpisodeListWithFilterQuery
import uz.graphql.EpisodeQuery
import uz.graphql.EpisodesWithIdsQuery
import uz.graphql.common_utills.other.ResponseApi
import uz.graphql.ricky_and_morty_data.model.FilterEpisodeDTO

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/14/2023 8:43 PM for Rick And Morty GraphQL.
 */
interface RepositoryEpisode {
    suspend fun getEpisodes(page:Int): ResponseApi<EpisodeListQuery.Episodes>
    suspend fun getEpisodesWithFilter(page: Int,filterData: FilterEpisodeDTO): ResponseApi<EpisodeListWithFilterQuery.Episodes>
    suspend fun getEpisode(id: String): ResponseApi<EpisodeQuery.Episode>
    suspend fun getEpisode(ls: List<String>): ResponseApi<List<EpisodesWithIdsQuery.EpisodesById?>>
}