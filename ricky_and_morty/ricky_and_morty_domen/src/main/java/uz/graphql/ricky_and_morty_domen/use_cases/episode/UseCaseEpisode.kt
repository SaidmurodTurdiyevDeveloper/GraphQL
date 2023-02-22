package uz.graphql.ricky_and_morty_domen.use_cases.episode



/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 3:01 PM for Rick And Morty GraphQL.
 */
data class UseCaseEpisode(
    val getEpisode: GetEpisode,
    val getEpisodesList: GetEpisodesList,
    val getEpisodesListWithFilter: GetEpisodesListWithFilter,
    val getEpisodesListWithIds: GetEpisodesListWithIds
)
