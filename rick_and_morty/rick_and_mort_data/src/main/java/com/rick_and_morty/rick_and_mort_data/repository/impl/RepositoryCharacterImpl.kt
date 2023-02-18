package com.rick_and_morty.rick_and_mort_data.repository.impl

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.rick_and_morty.common_utills.other.ResponseApi
import com.rick_and_morty.data_graphql.CharacterListQuery
import com.rick_and_morty.data_graphql.CharacterListWithFilterQuery
import com.rick_and_morty.data_graphql.CharacterQuery
import com.rick_and_morty.data_graphql.CharactersWithIdsQuery
import com.rick_and_morty.data_graphql.type.FilterCharacter
import com.rick_and_morty.rick_and_mort_data.model.FilterCharacterDTO
import com.rick_and_morty.rick_and_mort_data.repository.RepositoryCharacter
import java.io.IOException

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/14/2023 8:52 PM for Rick And Morty GraphQL.
 */
class RepositoryCharacterImpl(private var client: ApolloClient) : RepositoryCharacter {
    override suspend fun getCharacters(page: Int): ResponseApi<CharacterListQuery.Characters> {
        return try {
            val result = client.query(CharacterListQuery(page)).execute()
            val characters = result.data?.characters
            if (characters != null)
                ResponseApi.Success(characters)
            else ResponseApi.Error("Characters can not find")
        } catch (e: IOException) {
            ResponseApi.Error(e.message ?: "Unknown error")
        } catch (e: Exception) {
            ResponseApi.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getCharactersWithFilter(page: Int, filterData: FilterCharacterDTO): ResponseApi<CharacterListWithFilterQuery.Characters> {
        var filter = FilterCharacter()
        if (filterData.gender != null && filterData.gender.isNotBlank()) {
            filter = filter.copy(gender = Optional.present(filterData.gender))
        }
        if (filterData.name != null && filterData.name.isNotBlank()) {
            filter = filter.copy(name = Optional.present(filterData.name))
        }
        if (filterData.species != null && filterData.species.isNotBlank()) {
            filter = filter.copy(species = Optional.present(filterData.species))
        }
        if (filterData.status != null && filterData.status.isNotBlank()) {
            filter = filter.copy(status = Optional.present(filterData.status))
        }
        if (filterData.type != null && filterData.type.isNotBlank()) {
            filter = filter.copy(type = Optional.present(filterData.type))
        }
        return try {
            val result = client.query(CharacterListWithFilterQuery(filter, page)).execute()
            val characters = result.data?.characters
            if (characters != null)
                ResponseApi.Success(characters)
            else ResponseApi.Error("Characters can not find")
        } catch (e: IOException) {
            ResponseApi.Error(e.message ?: "Unknown error")
        } catch (e: Exception) {
            ResponseApi.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getCharacter(id: String): ResponseApi<CharacterQuery.Character> {
        return try {
            val result = client.query(CharacterQuery(id)).execute()
            val character = result.data?.character
            if (character != null)
                ResponseApi.Success(character)
            else ResponseApi.Error("Character is null")
        } catch (e: IOException) {
            ResponseApi.Error(e.message ?: "Unknown error")
        } catch (e: Exception) {
            ResponseApi.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getCharacters(ls: List<String>): ResponseApi<List<CharactersWithIdsQuery.CharactersById?>> {
        return try {
            val result = client.query(CharactersWithIdsQuery(ls)).execute()
            val characters = result.data?.charactersByIds
            if (characters != null && characters.isNotEmpty())
                ResponseApi.Success(characters)
            else ResponseApi.Error("Characters can not find")
        } catch (e: IOException) {
            ResponseApi.Error(e.message ?: "Unknown error")
        } catch (e: Exception) {
            ResponseApi.Error(e.message ?: "Unknown error")
        }
    }

}