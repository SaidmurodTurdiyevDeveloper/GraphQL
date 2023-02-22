package uz.graphql.ricky_and_morty_domen.use_cases.character

import uz.graphql.ricky_and_morty_domen.model.character.CharactersListData
import uz.graphql.ricky_and_morty_domen.model.character.toCharactersList
import kotlinx.coroutines.flow.Flow
import uz.graphql.common_utills.other.ResponseData
import uz.graphql.common_utills.other.invokeUseCase
import uz.graphql.ricky_and_morty_data.model.FilterCharacterDTO
import uz.graphql.ricky_and_morty_data.repository.RepositoryCharacter


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