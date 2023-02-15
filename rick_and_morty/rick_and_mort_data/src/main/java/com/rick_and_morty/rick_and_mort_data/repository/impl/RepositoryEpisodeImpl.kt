package com.rick_and_morty.rick_and_mort_data.repository.impl

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.rick_and_morty.common_utills.other.ResponseData
import com.rick_and_morty.data_graphql.*
import com.rick_and_morty.data_graphql.type.FilterCharacter
import com.rick_and_morty.data_graphql.type.FilterEpisode
import com.rick_and_morty.rick_and_mort_data.repository.RepositoryCharacter
import com.rick_and_morty.rick_and_mort_data.repository.RepositoryEpisode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import java.io.IOException

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/14/2023 8:52 PM for Rick And Morty GraphQL.
 */
class RepositoryEpisodeImpl(private var client: ApolloClient) : RepositoryEpisode {
    override suspend fun getEpisodes(page: Int): ResponseData<EpisodeListQuery.Episodes> {
        return try {
            val result = client.query(EpisodeListQuery(page)).execute()
            val episodes = result.data?.episodes
            if (episodes != null)
                ResponseData.Success(episodes)
            else ResponseData.Error("Episodes can not find")
        } catch (e: IOException) {
            ResponseData.Error(e.message ?: "Unknown error")
        } catch (e: Exception) {
            ResponseData.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getEpisodesWithFilter(page: Int, filter: FilterEpisode): ResponseData<EpisodeListWithFilterQuery.Episodes> {
        return try {
            val result = client.query(EpisodeListWithFilterQuery(filter, page)).execute()
            val episodes = result.data?.episodes
            if (episodes != null)
                ResponseData.Success(episodes)
            else ResponseData.Error("Episodes can not find")
        } catch (e: IOException) {
            ResponseData.Error(e.message ?: "Unknown error")
        } catch (e: Exception) {
            ResponseData.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getEpisode(id: String): ResponseData<EpisodeQuery.Episode> {
        return try {
            val result = client.query(EpisodeQuery(id)).execute()
            val episode = result.data?.episode
            if (episode != null)
                ResponseData.Success(episode)
            else ResponseData.Error("Character is null")
        } catch (e: IOException) {
            ResponseData.Error(e.message ?: "Unknown error")
        } catch (e: Exception) {
            ResponseData.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getEpisode(ls: List<String>): ResponseData<List<EpisodesWithIdsQuery.EpisodesById?>> {
        return try {
            val result = client.query(EpisodesWithIdsQuery(ls)).execute()
            val episodes = result.data?.episodesByIds
            if (episodes != null)
                ResponseData.Success(episodes)
            else ResponseData.Error("Episodes can not find")
        } catch (e: IOException) {
            ResponseData.Error(e.message ?: "Unknown error")
        } catch (e: Exception) {
            ResponseData.Error(e.message ?: "Unknown error")
        }
    }

}