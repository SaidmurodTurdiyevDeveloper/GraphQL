package com.rick_and_morty.domen.use_cases.character

import com.rick_and_morty.common_utills.other.ResponseData
import com.rick_and_morty.common_utills.other.invokeUseCase
import com.rick_and_morty.domen.model.character.CharactersListData
import com.rick_and_morty.domen.model.character.toCharactersList
import com.rick_and_morty.rick_and_mort_data.model.FilterCharacterDTO
import com.rick_and_morty.rick_and_mort_data.repository.RepositoryCharacter
import kotlinx.coroutines.flow.Flow


/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 11:28 AM for Rick And Morty GraphQL.
 */
class GetCharactersListWithFilter(private val repositoryCharacter: RepositoryCharacter) {
    operator fun invoke(
        page: Int,
        gender: String? = null,
        name: String? = null,
        species: String? = null,
        status: String? = null,
        type: String? = null
    ): Flow<ResponseData<List<CharactersListData>>> =
        invokeUseCase(
            page, FilterCharacterDTO(
                gender = gender,
                name = name,
                species = species,
                status = status,
                type = type
            ),
            repositoryCharacter::getCharactersWithFilter
        ) { characterList ->
            characterList.toCharactersList()
        }
}