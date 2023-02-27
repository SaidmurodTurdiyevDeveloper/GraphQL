package uz.graphql.ricky_and_morty_domen.use_cases.location

import uz.graphql.ricky_and_morty_domen.model.location.LocationsListData
import uz.graphql.ricky_and_morty_domen.model.location.toLocationsList
import kotlinx.coroutines.flow.Flow
import uz.graphql.common_utills.other.ResponseData
import uz.graphql.common_utills.other.invokeUseCase
import uz.graphql.ricky_and_morty_data.model.FilterLocationDTO
import uz.graphql.ricky_and_morty_data.repository.RepositoryLocation


/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 11:28 AM for Rick And Morty GraphQL.
 */
class GetLocationsListWithFilter(private val repositoryCharacter: RepositoryLocation) {
    private var currentPage = 0
    private var list = ArrayList<LocationsListData>()
    operator fun invoke(
        page: Int? = null,
        dimension: String? = null,
        name: String? = null,
        type: String? = null
    ): Flow<ResponseData<List<LocationsListData>>> =
        invokeUseCase(
            page?:currentPage, FilterLocationDTO(
                dimension  = dimension,
                name = name,
                type = type
            ),
            repositoryCharacter::getLocationsWithFilter
        ) { locations ->
            val newList = locations.toLocationsList()
            if (page == 0)
                list = ArrayList(newList)
            else
                list.addAll(newList)
            currentPage = locations.info?.next?:-1
            list
        }
}