package uz.graphql.ricky_and_morty_domen.use_cases.location

import uz.graphql.ricky_and_morty_domen.model.location.LocationDetailsData
import uz.graphql.ricky_and_morty_domen.model.location.toLocation
import kotlinx.coroutines.flow.Flow
import uz.graphql.common_utills.other.ResponseData
import uz.graphql.common_utills.other.invokeUseCase
import uz.graphql.ricky_and_morty_data.repository.RepositoryLocation

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 11:28 AM for Rick And Morty GraphQL.
 */
class GetLocation(private val repositoryCharacter: RepositoryLocation) {
    operator fun invoke(id: String): Flow<ResponseData<LocationDetailsData>> = invokeUseCase(id, repositoryCharacter::getLocation) { location ->
        location.toLocation()
    }

}