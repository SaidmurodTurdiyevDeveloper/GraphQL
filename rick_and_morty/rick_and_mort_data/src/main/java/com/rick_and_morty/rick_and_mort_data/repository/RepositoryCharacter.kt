package com.rick_and_morty.rick_and_mort_data.repository

import com.rick_and_morty.common_utills.other.ResponseData
import com.rick_and_morty.data_graphql.CharacterListQuery
import com.rick_and_morty.data_graphql.CharacterListWithFilterQuery
import com.rick_and_morty.data_graphql.CharacterQuery
import com.rick_and_morty.data_graphql.CharactersWithIdsQuery
import com.rick_and_morty.data_graphql.type.FilterCharacter

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/14/2023 8:43 PM for Rick And Morty GraphQL.
 */
interface RepositoryCharacter {
    suspend fun getCharacters(page:Int): ResponseData<CharacterListQuery.Characters>
    suspend fun getCharactersWithFilter(page: Int,filter:FilterCharacter): ResponseData<CharacterListWithFilterQuery.Characters>
    suspend fun getCharacter(id: String): ResponseData<CharacterQuery.Character>
    suspend fun getCharacter(ls: List<String>): ResponseData<List<CharactersWithIdsQuery.CharactersById?>>
}