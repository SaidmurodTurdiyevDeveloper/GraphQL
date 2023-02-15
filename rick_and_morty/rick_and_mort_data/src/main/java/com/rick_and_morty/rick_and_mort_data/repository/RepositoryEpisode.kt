package com.rick_and_morty.rick_and_mort_data.repository

import com.rick_and_morty.common_utills.other.ResponseData
import com.rick_and_morty.data_graphql.*
import com.rick_and_morty.data_graphql.type.FilterEpisode

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/14/2023 8:43 PM for Rick And Morty GraphQL.
 */
interface RepositoryEpisode {
    suspend fun getEpisodes(page:Int): ResponseData<EpisodeListQuery.Episodes>
    suspend fun getEpisodesWithFilter(page: Int,filter: FilterEpisode): ResponseData<EpisodeListWithFilterQuery.Episodes>
    suspend fun getEpisode(id: String): ResponseData<EpisodeQuery.Episode>
    suspend fun getEpisode(ls: List<String>): ResponseData<List<EpisodesWithIdsQuery.EpisodesById?>>
}