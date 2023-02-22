package uz.graphql.ricky_and_morty_domen.use_cases.episode

import uz.graphql.ricky_and_morty_domen.model.episode.EpisodesListData
import uz.graphql.ricky_and_morty_domen.model.episode.toEpisodesList
import kotlinx.coroutines.flow.Flow
import uz.graphql.common_utills.other.ResponseData
import uz.graphql.common_utills.other.invokeUseCase
import uz.graphql.ricky_and_morty_data.repository.RepositoryEpisode

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 11:28 AM for Rick And Morty GraphQL.
 */
class GetEpisodesList(private val repositoryCharacter: RepositoryEpisode) {
    operator fun invoke(page: Int): Flow<ResponseData<List<EpisodesListData>>> = invokeUseCase(page, repositoryCharacter::getEpisodes) { episodes ->
        episodes.toEpisodesList()
    }
}