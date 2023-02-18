package com.rick_and_morty.domen.use_cases.episode

import com.rick_and_morty.common_utills.other.ResponseData
import com.rick_and_morty.common_utills.other.invokeUseCase
import com.rick_and_morty.domen.model.episode.EpisodesListData
import com.rick_and_morty.domen.model.episode.toEpisodesList
import com.rick_and_morty.rick_and_mort_data.model.FilterEpisodeDTO
import com.rick_and_morty.rick_and_mort_data.repository.RepositoryEpisode
import kotlinx.coroutines.flow.Flow


/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 11:28 AM for Rick And Morty GraphQL.
 */
class GetEpisodesListWithFilter(private val repositoryCharacter: RepositoryEpisode) {
    operator fun invoke(
        page: Int,
        name: String? = null,
        episode: String? = null,
    ): Flow<ResponseData<List<EpisodesListData>>> =
        invokeUseCase(
            page, FilterEpisodeDTO(
                name = name,
                episode = episode
            ),
            repositoryCharacter::getEpisodesWithFilter
        ) { episodes ->
            episodes.toEpisodesList()
        }
}