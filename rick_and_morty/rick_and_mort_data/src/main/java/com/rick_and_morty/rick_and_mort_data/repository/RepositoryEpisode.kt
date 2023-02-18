package com.rick_and_morty.rick_and_mort_data.repository

import com.rick_and_morty.common_utills.other.ResponseApi
import com.rick_and_morty.data_graphql.*
import com.rick_and_morty.rick_and_mort_data.model.FilterEpisodeDTO

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/14/2023 8:43 PM for Rick And Morty GraphQL.
 */
interface RepositoryEpisode {
    suspend fun getEpisodes(page:Int): ResponseApi<EpisodeListQuery.Episodes>
    suspend fun getEpisodesWithFilter(page: Int,filterData: FilterEpisodeDTO): ResponseApi<EpisodeListWithFilterQuery.Episodes>
    suspend fun getEpisode(id: String): ResponseApi<EpisodeQuery.Episode>
    suspend fun getEpisode(ls: List<String>): ResponseApi<List<EpisodesWithIdsQuery.EpisodesById?>>
}