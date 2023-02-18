package com.rick_and_morty.domen.use_cases.location

import com.rick_and_morty.common_utills.other.ResponseData
import com.rick_and_morty.common_utills.other.invokeUseCase
import com.rick_and_morty.domen.model.location.LocationsListData
import com.rick_and_morty.domen.model.location.toLocationsList
import com.rick_and_morty.rick_and_mort_data.model.FilterLocationDTO
import com.rick_and_morty.rick_and_mort_data.repository.RepositoryLocation
import kotlinx.coroutines.flow.Flow


/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 11:28 AM for Rick And Morty GraphQL.
 */
class GetLocationsListWithFilter(private val repositoryCharacter: RepositoryLocation) {
    operator fun invoke(
        page: Int,
        dimension: String? = null,
        name: String? = null,
        type: String? = null
    ): Flow<ResponseData<List<LocationsListData>>> =
        invokeUseCase(
            page, FilterLocationDTO(
                dimension  = dimension,
                name = name,
                type = type
            ),
            repositoryCharacter::getLocationsWithFilter
        ) { locations ->
            locations.toLocationsList()
        }
}