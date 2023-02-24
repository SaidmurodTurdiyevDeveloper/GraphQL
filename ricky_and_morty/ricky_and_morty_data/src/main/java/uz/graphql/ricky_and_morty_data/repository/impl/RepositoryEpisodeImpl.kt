package uz.graphql.ricky_and_morty_data.repository.impl

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import uz.graphql.*
import uz.graphql.common_utills.other.ResponseApi
import uz.graphql.ricky_and_morty_data.model.FilterEpisodeDTO
import uz.graphql.ricky_and_morty_data.repository.RepositoryEpisode
import uz.graphql.type.FilterEpisode
import java.io.IOException

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/14/2023 8:52 PM for Rick And Morty GraphQL.
 */
class RepositoryEpisodeImpl(private var client: ApolloClient) : RepositoryEpisode {
    override suspend fun getEpisodes(page: Int): ResponseApi<EpisodeListQuery.Episodes> {
        return try {
            if (page < 0) {
                ResponseApi.Success(
                    EpisodeListQuery.Episodes(
                        info = null,
                        results = null
                    )
                )
            } else {
                val result = client.query(EpisodeListQuery(page)).execute()
                val episodes = result.data?.episodes
                if (episodes != null)
                    ResponseApi.Success(episodes)
                else ResponseApi.Error("Episodes can not find")
            }
        } catch (e: IOException) {
            ResponseApi.Error(e.message ?: "Unknown error")
        } catch (e: Exception) {
            ResponseApi.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getEpisodesWithFilter(page: Int, filterData: FilterEpisodeDTO): ResponseApi<EpisodeListWithFilterQuery.Episodes> {
        var filter = FilterEpisode()
        if (filterData.name != null && filterData.name.isNotBlank()) {
            filter = filter.copy(name = Optional.present(filterData.name))
        }
        if (filterData.episode != null && filterData.episode.isNotBlank()) {
            filter = filter.copy(episode = Optional.present(filterData.episode))
        }
        return try {
            if (page < 0) {
                ResponseApi.Success(
                    EpisodeListWithFilterQuery.Episodes(
                        info = null,
                        results = null
                    )
                )
            } else {
                val result = client.query(EpisodeListWithFilterQuery(filter, page)).execute()
                val episodes = result.data?.episodes
                if (episodes != null)
                    ResponseApi.Success(episodes)
                else ResponseApi.Error("Episodes can not find")
            }
        } catch (e: IOException) {
            ResponseApi.Error(e.message ?: "Unknown error")
        } catch (e: Exception) {
            ResponseApi.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getEpisode(id: String): ResponseApi<EpisodeQuery.Episode> {
        return try {
            val result = client.query(EpisodeQuery(id)).execute()
            val episode = result.data?.episode
            if (episode != null)
                ResponseApi.Success(episode)
            else ResponseApi.Error("Episode is null")
        } catch (e: IOException) {
            ResponseApi.Error(e.message ?: "Unknown error")
        } catch (e: Exception) {
            ResponseApi.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getEpisode(ls: List<String>): ResponseApi<List<EpisodesWithIdsQuery.EpisodesById?>> {
        return try {
            val result = client.query(EpisodesWithIdsQuery(ls)).execute()
            val episodes = result.data?.episodesByIds
            if (episodes != null)
                ResponseApi.Success(episodes)
            else ResponseApi.Error("Episodes can not find")
        } catch (e: IOException) {
            ResponseApi.Error(e.message ?: "Unknown error")
        } catch (e: Exception) {
            ResponseApi.Error(e.message ?: "Unknown error")
        }
    }

}