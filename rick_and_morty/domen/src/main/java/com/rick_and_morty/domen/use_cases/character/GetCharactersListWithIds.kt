package com.rick_and_morty.domen.use_cases.character

import com.rick_and_morty.common_utills.other.ResponseData
import com.rick_and_morty.common_utills.other.invokeUseCase
import com.rick_and_morty.domen.model.character.CharacterListWithDetailsData
import com.rick_and_morty.domen.model.character.toCharactersList
import com.rick_and_morty.rick_and_mort_data.repository.RepositoryCharacter
import kotlinx.coroutines.flow.Flow

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 11:28 AM for Rick And Morty GraphQL.
 */
class GetCharactersListWithIds(private val repositoryCharacter: RepositoryCharacter) {
    operator fun invoke(ids: List<String>): Flow<ResponseData<List<CharacterListWithDetailsData>>> = invokeUseCase(ids, repositoryCharacter::getCharacters) { characterList ->
        characterList.toCharactersList()
    }
}