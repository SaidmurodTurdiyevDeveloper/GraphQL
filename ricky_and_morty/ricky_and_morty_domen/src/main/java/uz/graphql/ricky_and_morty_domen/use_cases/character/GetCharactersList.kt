package uz.graphql.ricky_and_morty_domen.use_cases.character

import uz.graphql.ricky_and_morty_domen.model.character.CharactersListData
import uz.graphql.ricky_and_morty_domen.model.character.toCharactersList
import kotlinx.coroutines.flow.Flow
import uz.graphql.common_utills.other.ResponseData
import uz.graphql.common_utills.other.invokeUseCase
import uz.graphql.ricky_and_morty_data.repository.RepositoryCharacter

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 11:28 AM for Rick And Morty GraphQL.
 */
class GetCharactersList(private val repositoryCharacter: RepositoryCharacter) {
    operator fun invoke(page: Int): Flow<ResponseData<List<CharactersListData>>> = invokeUseCase(page, repositoryCharacter::getCharacters) { characterList ->
        characterList.toCharactersList()
    }
}