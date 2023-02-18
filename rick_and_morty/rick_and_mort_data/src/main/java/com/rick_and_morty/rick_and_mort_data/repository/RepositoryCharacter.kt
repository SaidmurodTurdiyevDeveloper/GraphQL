package com.rick_and_morty.rick_and_mort_data.repository

import com.rick_and_morty.common_utills.other.ResponseApi
import com.rick_and_morty.data_graphql.CharacterListQuery
import com.rick_and_morty.data_graphql.CharacterListWithFilterQuery
import com.rick_and_morty.data_graphql.CharacterQuery
import com.rick_and_morty.data_graphql.CharactersWithIdsQuery
import com.rick_and_morty.rick_and_mort_data.model.FilterCharacterDTO

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/14/2023 8:43 PM for Rick And Morty GraphQL.
 */
interface RepositoryCharacter {
    suspend fun getCharacters(page:Int): ResponseApi<CharacterListQuery.Characters>
    suspend fun getCharactersWithFilter(page: Int,filterData: FilterCharacterDTO): ResponseApi<CharacterListWithFilterQuery.Characters>
    suspend fun getCharacter(id: String): ResponseApi<CharacterQuery.Character>
    suspend fun getCharacters(ls: List<String>): ResponseApi<List<CharactersWithIdsQuery.CharactersById?>>
}