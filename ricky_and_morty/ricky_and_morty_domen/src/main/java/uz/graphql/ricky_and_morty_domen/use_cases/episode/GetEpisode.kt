package uz.graphql.ricky_and_morty_domen.use_cases.episode

import uz.graphql.ricky_and_morty_domen.model.episode.EpisodeDetailsData
import uz.graphql.ricky_and_morty_domen.model.episode.toEpisode
import kotlinx.coroutines.flow.Flow
import uz.graphql.common_utills.other.ResponseData
import uz.graphql.common_utills.other.invokeUseCase
import uz.graphql.ricky_and_morty_data.repository.RepositoryEpisode

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 11:28 AM for Rick And Morty GraphQL.
 */
class GetEpisode(private val repositoryCharacter: RepositoryEpisode) {
    operator fun invoke(id: String): Flow<ResponseData<EpisodeDetailsData>> = invokeUseCase(id, repositoryCharacter::getEpisode) { episode ->
        episode.toEpisode()
    }
}