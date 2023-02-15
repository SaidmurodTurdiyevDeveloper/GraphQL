package com.rick_and_morty.rick_and_mort_data.repository.impl

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.rick_and_morty.common_utills.other.ResponseData
import com.rick_and_morty.data_graphql.CharacterListQuery
import com.rick_and_morty.data_graphql.CharacterListWithFilterQuery
import com.rick_and_morty.data_graphql.CharacterQuery
import com.rick_and_morty.data_graphql.CharactersWithIdsQuery
import com.rick_and_morty.data_graphql.type.FilterCharacter
import com.rick_and_morty.rick_and_mort_data.repository.RepositoryCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import java.io.IOException

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/14/2023 8:52 PM for Rick And Morty GraphQL.
 */
class RepositoryCharacterImpl(private var client: ApolloClient) : RepositoryCharacter {
    override suspend fun getCharacters(page: Int): ResponseData<CharacterListQuery.Characters> {
        return try {
            val result = client.query(CharacterListQuery(page)).execute()
            val characters = result.data?.characters
            if (characters != null)
                ResponseData.Success(characters)
            else ResponseData.Error("Characters can not find")
        } catch (e: IOException) {
            ResponseData.Error(e.message ?: "Unknown error")
        } catch (e: Exception) {
            ResponseData.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getCharactersWithFilter(page: Int, filter: FilterCharacter): ResponseData<CharacterListWithFilterQuery.Characters> {
        return try {
            val result = client.query(CharacterListWithFilterQuery(filter, page)).execute()
            val characters = result.data?.characters
            if (characters != null)
                ResponseData.Success(characters)
            else ResponseData.Error("Characters can not find")
        } catch (e: IOException) {
            ResponseData.Error(e.message ?: "Unknown error")
        } catch (e: Exception) {
            ResponseData.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getCharacter(id: String): ResponseData<CharacterQuery.Character> {
        return try {
            val result = client.query(CharacterQuery(id)).execute()
            val character = result.data?.character
            if (character != null)
                ResponseData.Success(character)
            else ResponseData.Error("Character is null")
        } catch (e: IOException) {
            ResponseData.Error(e.message ?: "Unknown error")
        } catch (e: Exception) {
            ResponseData.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getCharacter(ls: List<String>): ResponseData<List<CharactersWithIdsQuery.CharactersById?>> {
        return try {
            val result = client.query(CharactersWithIdsQuery(ls)).execute()
            val characters = result.data?.charactersByIds
            if (characters != null)
                ResponseData.Success(characters)
            else ResponseData.Error("Characters can not find")
        } catch (e: IOException) {
            ResponseData.Error(e.message ?: "Unknown error")
        } catch (e: Exception) {
            ResponseData.Error(e.message ?: "Unknown error")
        }
    }

}