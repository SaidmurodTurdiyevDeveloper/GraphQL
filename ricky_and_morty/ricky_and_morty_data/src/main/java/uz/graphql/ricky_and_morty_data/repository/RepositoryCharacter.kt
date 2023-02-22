package uz.graphql.ricky_and_morty_data.repository

import uz.graphql.CharacterListQuery
import uz.graphql.CharacterListWithFilterQuery
import uz.graphql.CharacterQuery
import uz.graphql.CharactersWithIdsQuery
import uz.graphql.common_utills.other.ResponseApi
import uz.graphql.ricky_and_morty_data.model.FilterCharacterDTO

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/14/2023 8:43 PM for Rick And Morty GraphQL.
 */
interface RepositoryCharacter {
    suspend fun getCharacters(page:Int): ResponseApi<CharacterListQuery.Characters>
    suspend fun getCharactersWithFilter(page: Int,filterData: FilterCharacterDTO): ResponseApi<CharacterListWithFilterQuery.Characters>
    suspend fun getCharacter(id: String): ResponseApi<CharacterQuery.Character>
    suspend fun getCharacters(ls: List<String>): ResponseApi<List<CharactersWithIdsQuery.CharactersById?>>
}