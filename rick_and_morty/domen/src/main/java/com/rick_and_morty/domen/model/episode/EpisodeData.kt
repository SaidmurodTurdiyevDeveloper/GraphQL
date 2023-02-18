package com.rick_and_morty.domen.model.episode

import com.rick_and_morty.data_graphql.EpisodeQuery

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 3:15 PM for Rick And Morty GraphQL.
 */
data class EpisodeData(
    val episode: String,
    val name: String,
    val airData: String,
    val created: String,
    val characters: List<EpisodeCharactersData>
)

data class EpisodeCharactersData(
    val id: String,
    val name: String,
    val image: String,
    val created: String,
    val type: String,
    val status: String
)

fun EpisodeQuery.Episode.toEpisode(): EpisodeData {
    val newCharacters = characters.map { data ->
        EpisodeCharactersData(
            id = data?.id ?: "",
            name = data?.name ?: "",
            image = data?.image ?: "",
            created = data?.created ?: "",
            type = data?.type ?: "",
            status = data?.status ?: ""
        )
    }
    return EpisodeData(
        episode = episode ?: "",
        name = name ?: "",
        airData = air_date ?: "",
        created = created ?: "",
        characters = newCharacters
    )
}
