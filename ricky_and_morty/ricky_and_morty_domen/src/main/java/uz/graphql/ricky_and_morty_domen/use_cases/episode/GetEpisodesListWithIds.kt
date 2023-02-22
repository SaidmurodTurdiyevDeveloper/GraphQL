package uz.graphql.ricky_and_morty_domen.use_cases.episode

import uz.graphql.ricky_and_morty_domen.model.episode.EpisodeListWithDetailsData
import uz.graphql.ricky_and_morty_domen.model.episode.toEpisodesList
import kotlinx.coroutines.flow.Flow
import uz.graphql.common_utills.other.ResponseData
import uz.graphql.common_utills.other.invokeUseCase
import uz.graphql.ricky_and_morty_data.repository.RepositoryEpisode

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 11:28 AM for Rick And Morty GraphQL.
 */
class GetEpisodesListWithIds(private val repositoryCharacter: RepositoryEpisode) {
    operator fun invoke(ids: List<String>): Flow<ResponseData<List<EpisodeListWithDetailsData>>> = invokeUseCase(ids, repositoryCharacter::getEpisode) { episodes ->
        episodes.toEpisodesList()
    }
}