package uz.graphql.ricky_and_morty_domen.use_cases.character

import uz.graphql.ricky_and_morty_domen.model.character.CharacterData
import uz.graphql.ricky_and_morty_domen.model.character.toCharacter
import kotlinx.coroutines.flow.Flow
import uz.graphql.common_utills.other.ResponseData
import uz.graphql.common_utills.other.invokeUseCase
import uz.graphql.ricky_and_morty_data.repository.RepositoryCharacter

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 11:28 AM for Rick And Morty GraphQL.
 */
class GetCharacter(private val repositoryCharacter: RepositoryCharacter) {
    operator fun invoke(id: String): Flow<ResponseData<CharacterData>> = invokeUseCase(id, repositoryCharacter::getCharacter) { character ->
        character.toCharacter()
    }

}