package uz.graphql.ricky_and_morty_domen.use_cases.location

import uz.graphql.ricky_and_morty_domen.model.location.LocationsListData
import uz.graphql.ricky_and_morty_domen.model.location.toLocationsList
import kotlinx.coroutines.flow.Flow
import uz.graphql.common_utills.other.ResponseData
import uz.graphql.common_utills.other.invokeUseCase
import uz.graphql.ricky_and_morty_data.repository.RepositoryLocation

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 11:28 AM for Rick And Morty GraphQL.
 */
class GetLocationsList(private val repositoryCharacter: RepositoryLocation) {
    private var currentPage = 0
    private var list = ArrayList<LocationsListData>()
    operator fun invoke(page: Int? = null): Flow<ResponseData<List<LocationsListData>>> = invokeUseCase(page?:currentPage, repositoryCharacter::getLocations) { locations ->
        val newList = locations.toLocationsList()
        if (page == 0)
            list.clear()
        list.addAll(newList)
        currentPage = locations.info?.next?:-1
        list
    }
}