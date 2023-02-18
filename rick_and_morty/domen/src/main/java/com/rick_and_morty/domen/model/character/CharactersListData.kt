package com.rick_and_morty.domen.model.character

import com.rick_and_morty.data_graphql.CharacterListQuery
import com.rick_and_morty.data_graphql.CharacterListWithFilterQuery

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 11:47 AM for Rick And Morty GraphQL.
 */
data class CharactersListData(
    val id: String,
    val image: String,
    val name: String,
    val status: String,
    val created: String
)

fun CharacterListQuery.Characters.toCharactersList(): List<CharactersListData> {
    this.results
    return this.results?.map { data ->
        CharactersListData(
            id = data?.id ?: "",
            image = data?.image ?: "",
            name = data?.name ?: "",
            status = data?.status ?: "",
            created = data?.created ?: ""
        )
    } ?: emptyList()
}
fun CharacterListWithFilterQuery.Characters.toCharactersList(): List<CharactersListData> {
    this.results
    return this.results?.map { data ->
        CharactersListData(
            id = data?.id ?: "",
            image = data?.image ?: "",
            name = data?.name ?: "",
            status = data?.status ?: "",
            created = data?.created ?: ""
        )
    } ?: emptyList()
}
