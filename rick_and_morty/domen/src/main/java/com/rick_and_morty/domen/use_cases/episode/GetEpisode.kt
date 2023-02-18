package com.rick_and_morty.domen.use_cases.episode

import com.rick_and_morty.common_utills.other.ResponseData
import com.rick_and_morty.common_utills.other.invokeUseCase
import com.rick_and_morty.domen.model.episode.EpisodeData
import com.rick_and_morty.domen.model.episode.toEpisode
import com.rick_and_morty.rick_and_mort_data.repository.RepositoryEpisode
import kotlinx.coroutines.flow.Flow

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 11:28 AM for Rick And Morty GraphQL.
 */
class GetEpisode(private val repositoryCharacter: RepositoryEpisode) {
    operator fun invoke(id: String): Flow<ResponseData<EpisodeData>> = invokeUseCase(id, repositoryCharacter::getEpisode) { episode ->
        episode.toEpisode()
    }
}