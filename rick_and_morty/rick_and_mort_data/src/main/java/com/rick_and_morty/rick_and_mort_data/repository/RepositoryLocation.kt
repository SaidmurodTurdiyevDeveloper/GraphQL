package com.rick_and_morty.rick_and_mort_data.repository

import com.rick_and_morty.common_utills.other.ResponseApi
import com.rick_and_morty.data_graphql.*
import com.rick_and_morty.rick_and_mort_data.model.FilterLocationDTO

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/14/2023 8:43 PM for Rick And Morty GraphQL.
 */
interface RepositoryLocation {
    suspend fun getLocations(page:Int): ResponseApi<LocationListQuery.Locations>
    suspend fun getLocationsWithFilter(page: Int,filterData: FilterLocationDTO): ResponseApi<LocationListWithFilterQuery.Locations>
    suspend fun getLocation(id: String): ResponseApi<LocationQuery.Location>
    suspend fun getLocation(ls: List<String>): ResponseApi<List<LocationsWithIdsQuery.LocationsById?>>
}