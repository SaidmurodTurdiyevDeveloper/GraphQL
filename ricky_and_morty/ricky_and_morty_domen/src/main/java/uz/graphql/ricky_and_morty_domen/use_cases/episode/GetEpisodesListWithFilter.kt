package uz.graphql.ricky_and_morty_domen.use_cases.episode

import uz.graphql.ricky_and_morty_domen.model.episode.EpisodesListData
import uz.graphql.ricky_and_morty_domen.model.episode.toEpisodesList
import kotlinx.coroutines.flow.Flow
import uz.graphql.common_utills.other.ResponseData
import uz.graphql.common_utills.other.invokeUseCase
import uz.graphql.ricky_and_morty_data.model.FilterEpisodeDTO
import uz.graphql.ricky_and_morty_data.repository.RepositoryEpisode


/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 11:28 AM for Rick And Morty GraphQL.
 */
class GetEpisodesListWithFilter(private val repositoryCharacter: RepositoryEpisode) {
    private var currentPage = 0
    private var list = ArrayList<EpisodesListData>()
    operator fun invoke(
        name: String? = null,
        episode: String? = null,
    ): Flow<ResponseData<List<EpisodesListData>>> =
        invokeUseCase(
            currentPage,
            FilterEpisodeDTO(
                name = name,
                episode = episode
            ),
            repositoryCharacter::getEpisodesWithFilter
        ) { episodes ->
            list.addAll(episodes.toEpisodesList())
            currentPage = episodes.info?.next ?: -1
            list
        }
}