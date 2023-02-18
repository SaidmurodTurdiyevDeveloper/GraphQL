package com.rick_and_morty.domen.use_cases.location

import com.rick_and_morty.common_utills.other.ResponseData
import com.rick_and_morty.common_utills.other.invokeUseCase
import com.rick_and_morty.domen.model.location.LocationListWithDetailsData
import com.rick_and_morty.domen.model.location.toLocationsList
import com.rick_and_morty.rick_and_mort_data.repository.RepositoryLocation
import kotlinx.coroutines.flow.Flow

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 11:28 AM for Rick And Morty GraphQL.
 */
class GetLocationsListWithIds(private val repositoryCharacter: RepositoryLocation) {
    operator fun invoke(ids: List<String>): Flow<ResponseData<List<LocationListWithDetailsData>>> = invokeUseCase(ids, repositoryCharacter::getLocation) { locations ->
        locations.toLocationsList()
    }
}